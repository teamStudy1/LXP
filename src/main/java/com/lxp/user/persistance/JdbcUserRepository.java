package com.lxp.user.persistance;

import com.lxp.user.domain.model.User;
import com.lxp.user.domain.model.enums.UserRole;
import com.lxp.user.domain.repository.UserRespository;
import com.lxp.user.persistance.dao.UserDao;
import com.lxp.user.persistance.dao.UserProfileDao;
import com.lxp.user.persistance.mapper.UserMapper;
import com.lxp.user.persistance.row.UserRow;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcUserRepository implements UserRespository {
    private final UserDao userDao;
    private final UserProfileDao userProfileDao;

    public JdbcUserRepository(UserDao userDao, UserProfileDao userProfileDao) {
        this.userDao = userDao;
        this.userProfileDao = userProfileDao;
    }

    @Override
    public Optional<User> findUserById(Long id) {
        try {
            Optional<UserRow> row = userDao.findUserById(id);
            return row.map(UserMapper::toDomain);
        } catch (SQLException e) {
            throw new IllegalStateException("사용자 조회에 실패했습니다.");
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        try {
            return userDao.existsByEmail(email);
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean existsById(Long id) {
        try {
            return userDao.existsById(id);
        } catch (SQLException e) {
            throw new IllegalStateException("존재하지 않는 사용자 입니다.");
        }
    }

    @Override
    public Optional<UserRole> findUserRoleById(Long id) {
        try {
            return userDao.findRoleById(id);
        } catch (SQLException e) {
            throw new IllegalStateException("사용자 권한 조회에 실패했습니다.");
        }
    }

    @Override
    public void save(User user) throws Exception {
        UserRow userRow = UserMapper.toRow(user);
        try {
            long userId = userDao.saveUser(userRow);
            userProfileDao.saveUserProfile(userId, userRow.userProfile());
        } catch (Exception e) {
            throw new IllegalStateException("사용자 저장에 실패했습니다." + e.getMessage());
        }
    }

    @Override
    public void update(User user) throws IllegalStateException {
        try {
            long result = userDao.update(UserMapper.toRow(user));
            if (result == 0) throw new IllegalStateException();
        } catch (SQLException e) {
            throw new IllegalStateException("사용자 업데이트에 실패했습니다." + e.getMessage());
        }
    }
}
