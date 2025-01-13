package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(String roleMember);

    List<Role> findByUsers_UserId(Integer userId);
}
