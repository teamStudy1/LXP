package com.lxp.user.web;

import com.lxp.user.web.dto.request.CreateUserRequest;
import com.lxp.user.web.dto.request.UpdateUserRoleRequest;
import com.lxp.user.web.dto.response.UserResponse;
import com.lxp.user.domain.model.enums.UserRole;
import com.lxp.user.service.UserService;
import java.sql.SQLException;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public UserResponse getUserById(Long id) throws Exception {
        return userService.getUserViewById(id);
    }

    public UserRole getUserRoleById(Long id) throws Exception {
        return userService.getUserRoleById(id);
    }

    public void saveUser(CreateUserRequest userRequest) throws SQLException {
        userRequest.validate();
        userService.saveUser(userRequest);
    }

    public void updateUserRole(UpdateUserRoleRequest userRoleRequest) throws SQLException {
        userRoleRequest.validate();
        userService.updateUserRole(userRoleRequest.userId(), userRoleRequest.getUserRole());
    }

    public void deactivateUser(Long userId) throws SQLException {
        if (userId <= 0) throw new IllegalStateException("유효하지 않은 사용자 id 값입니다.");
        userService.deactivateUser(userId);
    }
}
