package com.backendserver.DigitronixProject.services.User;

import com.backendserver.DigitronixProject.dtos.UpdateUserDTO;
import com.backendserver.DigitronixProject.dtos.UserDTO;
import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.models.User;

import java.util.List;
import java.util.Map;

public interface IUserService {
    User CreateUser(UserDTO userDTO) throws DataNotFoundException;
    Map<String, Object> Login(String username, String password) throws DataNotFoundException;

    public List<User> getAllUsers() throws Exception;

    public User updateUserInforByAdmin(Long userId, UpdateUserDTO updatedUserDTO) throws Exception;
}
