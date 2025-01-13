package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String value);

    boolean existsByEmail(String value);

    User findByEmail(@Email(message = "Invalid Email.") String email);

    Page<User> findAll(Specification<User> spec, Pageable pageable);
}
