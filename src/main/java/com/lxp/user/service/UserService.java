package com.lxp.user.service;

import com.lxp.user.domain.repository.UserRespository;
import com.lxp.user.web.dto.request.CreateUserRequest;
import com.lxp.user.web.dto.response.UserResponse;
import com.lxp.config.TransactionManager;
import com.lxp.user.domain.model.User;
import com.lxp.user.domain.model.enums.UserRole;
import java.sql.SQLException;

public class UserService {
    private final UserRespository userRespository;

    public UserService(UserRespository userRespository) {
        this.userRespository = userRespository;
    }

    public UserResponse getUserViewById(Long id) {
        User user = userRespository.findUserById(id)
            .orElseThrow(() -> new IllegalStateException("존재하지 않는 사용자 입니다."));;

        return UserResponse.from(user);
    }

    public UserRole getUserRoleById(Long id) {
        userRespository.findUserRoleById(id);
        boolean isNotExistsUser = !userRespository.existsById(id);
        if (isNotExistsUser) {
            throw new IllegalStateException("존재하지 않는 사용자 입니다. ");
        }
        return userRespository.findUserRoleById(id).orElseThrow(() -> new IllegalStateException("권한 조회에 실패했습니다."));
    }

    public void saveUser(CreateUserRequest request) throws SQLException {
        boolean isExistsUser = userRespository.existsByEmail(request.email());
        if (isExistsUser) {
            throw new IllegalStateException("이미 가입된 이메일입니다.");
        }

        try {
            TransactionManager.beginTransaction();
            userRespository.save(User.create(request));
            TransactionManager.commit();
        } catch (Exception ex) {
            TransactionManager.rollback();
            throw new SQLException();
        } finally {
            TransactionManager.close();
        }
    }

    public void updateUserRole(Long userId, UserRole userRole) throws SQLException {
        User user =
                userRespository
                        .findUserById(userId)
                        .orElseThrow(() -> new IllegalStateException("유효하지 않은 사용자 입니다."));
        user.updateUserRole(userRole);

        try {
            TransactionManager.beginTransaction();
            userRespository.update(user);
            System.out.println("사용자 권한 변경에 성공했습니다.");
            TransactionManager.commit();
        } catch (SQLException e) {
            TransactionManager.rollback();
            throw new RuntimeException();
        } finally {
            TransactionManager.close();
        }
    }

    public void deactivateUser(Long userId) throws SQLException {
        User user =
                userRespository
                        .findUserById(userId)
                        .orElseThrow(() -> new IllegalStateException("유효하지 않은 사용자 입니다."));
        if (user.isWithdrawal()) throw new IllegalStateException("이미 탈퇴한 사용자 입니다.");

        user.withdrawal();

        try {
            TransactionManager.beginTransaction();
            userRespository.update(user);
            System.out.println("사용자 탈퇴에 성공했습니다.");
            TransactionManager.commit();
        } catch (SQLException e) {
            TransactionManager.rollback();
            throw new RuntimeException();
        } finally {
            TransactionManager.close();
        }
    }
}
