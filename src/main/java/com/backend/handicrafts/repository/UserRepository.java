package com.backend.handicrafts.repository;

import com.backend.handicrafts.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
