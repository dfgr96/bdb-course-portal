package com.portal.auth.domain.repository;

import com.portal.dto.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {

    User save(User user);
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
}
