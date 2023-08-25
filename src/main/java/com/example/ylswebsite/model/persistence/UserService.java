package com.example.ylswebsite.model.persistence;

import com.example.ylswebsite.dto.UserDTO;
import com.example.ylswebsite.model.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDTO userDto);

    User findUserByEmail(String email);

    List<UserDTO> findAllUsers();
}
