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

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public ActiveStatus getActiveStatus() {
        return activeStatus;
    }

    public UserRole getRole() {
        return role;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
}
