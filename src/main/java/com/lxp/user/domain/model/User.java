package com.lxp.user.domain.model;

import com.lxp.user.domain.model.enums.ActiveStatus;
import com.lxp.user.domain.model.enums.UserRole;
import com.lxp.user.web.dto.request.CreateUserRequest;
import com.lxp.util.SHA256Util;
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
            UserProfile userProfile,
            Timestamp createdAt,
            Timestamp updatedAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.activeStatus = activeStatus;
        this.role = role;
        this.profile = userProfile;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static User create(CreateUserRequest request) {
        return new User(
            null,
            request.email(),
            SHA256Util.getSHA256Hash(request.password()),
            request.name(),
            ActiveStatus.ACTIVE,
            UserRole.STUDENT,
            new UserProfile(
                request.introduction(),
                request.resume()
            ),
            null,
            null
        );
    }

    public void updateUserRole(UserRole userRole) {
        if (userRole == UserRole.ADMIN) throw new IllegalStateException("관리자 권한으로는 변경할 수 없습니다.");
        if (userRole == role) throw new IllegalStateException("이미 " + userRole.name() + " 권한입니다.");
        if (userRole == UserRole.STUDENT && role == UserRole.INSTRUCTOR)
            throw new IllegalStateException("INSTRUCTOR에서 STUDENT가 될 수 없습니다.");
        role = userRole;
    }

    public void withdrawal() {
        if (role == UserRole.ADMIN) throw new IllegalStateException("관리자는 탈퇴할 수 없습니다.");
        if (activeStatus == ActiveStatus.DEACTIVE) throw new IllegalStateException("이미 탈퇴한 사용자 입니다.");
        activeStatus = ActiveStatus.DEACTIVE;
    }

    public boolean isWithdrawal() {
        return activeStatus == ActiveStatus.DEACTIVE;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
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

    public UserRole getUserRole() {
        return role;
    }

    public ActiveStatus getActiveStatus() {
        return activeStatus;
    }

    public UserProfile getProfile() { return profile; }
}
