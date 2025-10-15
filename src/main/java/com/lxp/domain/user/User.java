package com.lxp.domain.user;

import com.lxp.domain.user.enums.ActiveStatus;
import com.lxp.domain.user.enums.UserRole;
import java.sql.Timestamp;

public class User {
    private Long id;
    private String name;
    private String password;
    private String nickname;
    private ActiveStatus activeStatus;
    private UserRole role;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public User(
            Long id,
            String name,
            String password,
            String nickname,
            ActiveStatus activeStatus,
            UserRole role,
            Timestamp createdAt,
            Timestamp updatedAt) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.activeStatus = activeStatus;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
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
