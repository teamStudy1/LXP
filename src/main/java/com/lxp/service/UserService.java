package com.lxp.service;

import com.lxp.config.TransactionManager;
import com.lxp.domain.user.User;
import com.lxp.domain.user.enums.UserRole;
import com.lxp.infrastructure.dao.UserDao;
import java.sql.SQLException;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUserById(Long id) throws Exception {
        try {
            TransactionManager.beginTransaction();
            return userDao.findById(TransactionManager.getConnection(), id).orElseThrow(() -> new IllegalStateException("존재하지 않는 사용자 입니다."));
        } finally {
            TransactionManager.close();
        }
    }

    public User getUserByName(String username) throws SQLException {
        try {
            TransactionManager.beginTransaction();
            return userDao
                    .findByName(TransactionManager.getConnection(), username)
                    .orElseThrow(() -> new IllegalStateException("존재하지 않는 사용자 입니다."));
        } finally {
            TransactionManager.close();
        }
    }

    public UserRole getUserRoleById(Long id) throws Exception {
        getUserById(id);

        try {
            TransactionManager.beginTransaction();
            return userDao.findRoleById(TransactionManager.getConnection(), id).orElseThrow(() -> new IllegalStateException("권한 조회에 실패했습니다."));
        } finally {
            TransactionManager.close();
        }
    }
}
