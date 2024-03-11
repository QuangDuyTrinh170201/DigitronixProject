package com.backendserver.DigitronixProject.services;

import com.backendserver.DigitronixProject.dtos.UserDTO;
import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.models.User;

public interface IUserService {
    User CreateUser(UserDTO userDTO) throws DataNotFoundException;
    String Login(String username, String password) throws DataNotFoundException;
}
