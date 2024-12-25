package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.MembershipAmount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipAmountRepository extends JpaRepository<MembershipAmount, Integer> {
}
