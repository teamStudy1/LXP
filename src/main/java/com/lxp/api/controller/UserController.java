package com.lxp.api.controller;

import com.lxp.api.dto.CreateUserRequest;
import com.lxp.api.dto.UserResponse;
import com.lxp.domain.user.enums.UserRole;
import com.lxp.service.UserService;

import java.sql.SQLException;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public UserResponse getUserById(Long id) throws Exception {
        return userService.getUserById(id);
    }

    public UserRole getUserRoleById(Long id) throws Exception {
        return userService.getUserRoleById(id);
    }

    public long saveUser(CreateUserRequest userRequest) throws SQLException {
        userRequest.validate();
        return userService.saveUser(userRequest);
    }
}
