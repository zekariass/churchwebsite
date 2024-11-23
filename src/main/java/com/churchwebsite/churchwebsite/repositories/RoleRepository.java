package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
