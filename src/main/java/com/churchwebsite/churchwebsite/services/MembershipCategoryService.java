package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.MembershipCategory;
import com.churchwebsite.churchwebsite.repositories.MembershipCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipCategoryService {
    private final MembershipCategoryRepository membershipCategoryRepository;

    @Autowired
    public MembershipCategoryService(MembershipCategoryRepository membershipCategoryRepository) {
        this.membershipCategoryRepository = membershipCategoryRepository;
    }

    public MembershipCategory save(MembershipCategory membershipCategory) {
        return membershipCategoryRepository.save(membershipCategory);
    }

    public List<MembershipCategory> findAll() {
        return membershipCategoryRepository.findAll();
    }

    public MembershipCategory findById(int memId) {
        return membershipCategoryRepository.findById(memId).orElseThrow(()-> new RuntimeException("No membership category found with id: "+memId));
    }

    public void deleteById(int memId) {
        membershipCategoryRepository.deleteById(memId);
    }
}
