package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.Role;
import com.churchwebsite.churchwebsite.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Set<Role> findAll(){
        return new HashSet<>(roleRepository.findAll());
    }

}
