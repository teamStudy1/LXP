package com.lxp.domain.user;

import com.lxp.domain.user.enums.ActiveStatus;
import com.lxp.domain.user.enums.UserRole;
import java.sql.Timestamp;

public class User {
    private Long id;
    private String email;
    private String password;
    private String name;
    private ActiveStatus activeStatus;
    private UserRole role;
    private UserProfile profile;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public User(
            Long id,
            String email,
            String password,
            String name,
            ActiveStatus activeStatus,
            UserRole role,
            Timestamp createdAt,
            Timestamp updatedAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.activeStatus = activeStatus;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void updateUserRole(UserRole userRole) {
        if (userRole == UserRole.ADMIN) throw new IllegalStateException("관리자 권한으로는 변경할 수 없습니다.");
        if (userRole == role) throw new IllegalStateException("이미 " + userRole.name() + " 권한입니다.");
        if (userRole == UserRole.STUDENT && role == UserRole.INSTRUCTOR) throw new IllegalStateException("INSTRUCTOR에서 STUDENT가 될 수 없습니다.");
        role = userRole;
    }

    public UserRole getUserRole() {
        return role;
    }
}
