package com.lxp.user.domain.repository;

import com.lxp.user.domain.model.User;
import com.lxp.user.domain.model.enums.UserRole;
import java.util.Optional;

public interface UserRespository {
    Optional<User> findUserById(Long id);
    boolean existsByEmail(String email);
    boolean existsById(Long id);
    Optional<UserRole> findUserRoleById(Long id);
    void save(User user) throws Exception;
    void update(User user);
}
