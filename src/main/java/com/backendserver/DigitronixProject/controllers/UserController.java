package com.backendserver.DigitronixProject.controllers;

import com.backendserver.DigitronixProject.dtos.*;
import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.models.User;
import com.backendserver.DigitronixProject.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService userService;
    @PostMapping("/register")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult result){
        try{
            if(result.hasErrors())
            {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())){
                return ResponseEntity.badRequest().body("Password does not match");
            }
            User user = userService.CreateUser(userDTO);
            //return ResponseEntity.ok("Create user successfully");
            return ResponseEntity.ok(user);

        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO){
//        try {
//            String token = userService.Login(userLoginDTO.getUserName(), userLoginDTO.getPassword());
//            return ResponseEntity.ok(token);
//        } catch (DataNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        try {
            Map<String, Object> response = userService.Login(userLoginDTO.getUserName(), userLoginDTO.getPassword());
            return ResponseEntity.ok(response);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update/{userId}")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<?> updateUserByAdmin(@PathVariable Long userId, @RequestBody UpdateUserDTO updatedUserDTO) {
        try {
            User updatedUser = userService.updateUserInforByAdmin(userId, updatedUserDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user information: " + e.getMessage());
        }
    }
}
