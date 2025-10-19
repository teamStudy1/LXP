package com.lxp.service;

import com.lxp.api.dto.CreateUserRequest;
import com.lxp.api.dto.UserResponse;
import com.lxp.config.TransactionManager;
import com.lxp.domain.user.User;
import com.lxp.domain.user.enums.UserRole;
import com.lxp.infrastructure.dao.UserDao;
import com.lxp.infrastructure.mapper.UserMapper;
import com.lxp.service.query.UserView;
import com.lxp.util.SHA256Util;
import java.sql.SQLException;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserResponse getUserViewById(Long id) throws Exception {
        UserView userView =
                userDao
                        .findUserViewById(id)
                        .orElseThrow(() -> new IllegalStateException("존재하지 않는 사용자 입니다."));
        return UserMapper.toUserResponse(userView);
    }

    public UserRole getUserRoleById(Long id) throws Exception {
        boolean isNotExistsUser = !userDao.existsById(id);
        if (isNotExistsUser) {
            throw new IllegalStateException("존재하지 않는 사용자 입니다. ");
        }
        return userDao.findRoleById(id).orElseThrow(() -> new IllegalStateException("권한 조회에 실패했습니다."));
    }

    public long saveUser(CreateUserRequest userRequest) throws SQLException {
        boolean isExistsUser = userDao.existsByEmail(userRequest.email());
        if (isExistsUser) {
            throw new IllegalStateException("이미 가입된 이메일입니다.");
        }

        try {
            TransactionManager.beginTransaction();
            Long userId =
                    userDao.saveUser(
                            userRequest.email(),
                            SHA256Util.getSHA256Hash(userRequest.password()),
                            userRequest.name());
            if (userId == null) {
                throw new IllegalStateException("사용자 가입에 실패했습니다.");
            }

            Long userProfileId =
                    userDao.saveUserProfile(userId, userRequest.introduction(), userRequest.resume());
            if (userProfileId == null) {
                throw new IllegalStateException("사용자 가입에 실패했습니다.");
            }

            TransactionManager.commit();
            return userId;
        } catch (SQLException ex) {
            TransactionManager.rollback();
            throw ex;
        } finally {
            TransactionManager.close();
        }
    }

    public void updateUserRole(Long userId, UserRole userRole) throws SQLException {
        User user =
                userDao
                        .findUserById(userId)
                        .orElseThrow(() -> new IllegalStateException("유효하지 않은 사용자 입니다."));
        user.updateUserRole(userRole);

        try {
            TransactionManager.beginTransaction();
            int result = userDao.updateUserRole(userId, user.getUserRole());
            if (result == 0) {
                throw new IllegalStateException("사용자 권한 변경에 실패했습니다.");
            }
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
                userDao
                        .findUserById(userId)
                        .orElseThrow(() -> new IllegalStateException("유효하지 않은 사용자 입니다."));
        if (user.isWithdrawal()) throw new IllegalStateException("이미 탈퇴한 사용자 입니다.");

        user.withdrawal();

        try {
            TransactionManager.beginTransaction();
            int result = userDao.updateUserActiveStatus(userId, user.getActiveStatus());
            if (result == 0) {
                throw new IllegalStateException("사용자 탈퇴에 실패했습니다.");
            }
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
