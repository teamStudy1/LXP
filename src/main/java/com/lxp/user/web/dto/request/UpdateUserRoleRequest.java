package com.lxp.user.web.dto.request;

import com.lxp.user.domain.model.enums.UserRole;

public record UpdateUserRoleRequest(long userId, String userRole) {
    public void validate() {
        if (userId <= 0) throw new IllegalStateException("유효하지 않은 사용자 id 값입니다.");

        if (userRole == null || userRole.isEmpty()) throw new IllegalStateException("사용자 권한은 필수 값입니다.");
        if (!UserRole.isValid(userRole)) throw new IllegalStateException("정의 되지 않은 권한 값입니다.");
    }

    public UserRole getUserRole() {
        return UserRole.valueOf(userRole);
    }
}
