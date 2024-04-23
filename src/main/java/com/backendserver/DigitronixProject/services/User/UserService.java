package com.backendserver.DigitronixProject.services.User;

import com.backendserver.DigitronixProject.components.JwtTokenUtil;
import com.backendserver.DigitronixProject.dtos.UpdateUserDTO;
import com.backendserver.DigitronixProject.dtos.UserDTO;
import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.models.Role;
import com.backendserver.DigitronixProject.models.User;
import com.backendserver.DigitronixProject.repositories.RoleRepository;
import com.backendserver.DigitronixProject.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    @Override
    public User CreateUser(UserDTO userDTO) throws DataNotFoundException {
        String username = userDTO.getUserName();
        //check exist username
        if(userRepository.existsByUsername(username)){
            throw new DataIntegrityViolationException("Username already exists");
        }
        User newUser = User.builder()
                .username(userDTO.getUserName())
                .password(userDTO.getPassword())
                .active(true)
                .build();
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role Not Found"));
        newUser.setRole(role);
        String password = userDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);

        return userRepository.save(newUser);
    }



    @Override
    public Map<String, Object> Login(String username, String password) throws DataNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isEmpty()){
            throw new DataNotFoundException("Username or password is invalid");
        }
        User existingUser = optionalUser.get();
        //check password
        if(!passwordEncoder.matches(password, existingUser.getPassword())){
            throw new BadCredentialsException("Wrong username or password!");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username, password,
                existingUser.getAuthorities()
        );
        //authenticate with java spring security
        authenticationManager.authenticate(authenticationToken);

        // Prepare the response map
        Map<String, Object> response = new HashMap<>();
        response.put("token", jwtTokenUtil.generateToken(existingUser));
        response.put("roleId", existingUser.getRole().getId());
        response.put("username", existingUser.getUsername());
        response.put("userId", existingUser.getId());// Assuming Role is a field in User entity

        return response;
    }



    @Override
    public List<User> getAllUsers() throws Exception {
        try {
            List<User> users = userRepository.findAll();
            if (users.isEmpty()) {
                throw new DataNotFoundException("No users found");
            }
            return users;
        } catch (Exception e) {
            throw new Exception("Failed to retrieve users: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public User updateUserInforByAdmin(Long userId, UpdateUserDTO updatedUserDTO) throws Exception {
        // Tìm người dùng hiện có dựa vào userId
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        // Cập nhật trường isActive nếu có
        Boolean isActive = updatedUserDTO.getIsActive();
        if (isActive != null) {
            existingUser.setActive(isActive);
        }

        // Cập nhật trường roleId nếu có
        Long roleId = updatedUserDTO.getRoleId();
        if (roleId != null) {
            // Truy vấn cơ sở dữ liệu để lấy đối tượng Role tương ứng với roleId
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new DataNotFoundException("Role not found"));

            // Gán đối tượng Role vào trường role của User
            existingUser.setRole(role);
        }

        return userRepository.save(existingUser);
    }
}
