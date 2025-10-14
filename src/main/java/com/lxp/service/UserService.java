package com.lxp.service;

import com.lxp.infrastructure.dao.UserDao;
import com.lxp.infrastructure.row.UserRow;
import java.sql.SQLException;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserRow getUserById(Long id) {
        try {
            return userDao.findById(id).orElseThrow(() -> new IllegalStateException("존재하지 않는 사용자 입니다."));
        } catch (SQLException e) {
            System.out.println("sql Exception Error" + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public UserRow getUserByName(String username) {
        try {
            return userDao
                    .findByName(username)
                    .orElseThrow(() -> new IllegalStateException("존재하지 않는 사용자 입니다."));
        } catch (SQLException e) {
            System.out.println("sql Exception Error" + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
