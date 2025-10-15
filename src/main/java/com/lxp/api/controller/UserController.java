package com.lxp.api.controller;

import com.lxp.api.dto.UserResponse;
import com.lxp.domain.user.User;
import com.lxp.service.UserService;

import java.sql.SQLException;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public UserResponse getUserById(Long id) {
        try {
            User user = userService.getUserById(id);
            return UserResponse.from(user);
        } catch (SQLException e) {
            System.out.println("사용자 조회에 실패했습니다." + e.getMessage());
        }
        return null;
    }
}
