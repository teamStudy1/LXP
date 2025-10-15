package com.lxp.api.controller;

import com.lxp.api.dto.UserResponse;
import com.lxp.domain.user.User;
import com.lxp.domain.user.enums.UserRole;
import com.lxp.service.UserService;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public UserResponse getUserById(Long id) throws Exception {
        User user = userService.getUserById(id);
        return UserResponse.from(user);
    }

    public UserRole getUserRoleById(Long id) throws Exception {
        return userService.getUserRoleById(id);
    }
}
