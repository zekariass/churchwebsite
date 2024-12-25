package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.MembershipAmount;
import com.churchwebsite.churchwebsite.repositories.MembershipAmountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipAmountService {
    private final MembershipAmountRepository membershipAmountRepository;

    @Autowired
    public MembershipAmountService(MembershipAmountRepository membershipAmountRepository) {
        this.membershipAmountRepository = membershipAmountRepository;
    }

    public MembershipAmount save(MembershipAmount membershipAmount) {
        return membershipAmountRepository.save(membershipAmount);
    }

    public List<MembershipAmount> findAll() {
        return membershipAmountRepository.findAll();
    }

    public MembershipAmount findById(int memId) {
        return membershipAmountRepository.findById(memId).orElseThrow(()-> new RuntimeException("No membership category found with id: "+memId));
    }

    public void deleteById(int memId) {
        membershipAmountRepository.deleteById(memId);
    }
}
