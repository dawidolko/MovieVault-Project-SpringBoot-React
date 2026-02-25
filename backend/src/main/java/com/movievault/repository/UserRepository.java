package com.movievault.repository;

import com.movievault.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmailIgnoreCase(String email);
    Optional<User> findByResetToken(String resetToken);
    long countByRoleId(Long roleId);
}
