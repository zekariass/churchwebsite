package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.repositories.MemberDependentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberDependentService {
    private final MemberDependentRepository memberDependentRepository;

    @Autowired
    public MemberDependentService(MemberDependentRepository memberDependentRepository) {
        this.memberDependentRepository = memberDependentRepository;
    }
}
