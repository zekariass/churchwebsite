package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.Role;
import com.churchwebsite.churchwebsite.entities.User;
import com.churchwebsite.churchwebsite.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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



    public List<Role> findByUser(User user) {
        return roleRepository.findByUsers_UserId(user.getUserId());
    }
}
