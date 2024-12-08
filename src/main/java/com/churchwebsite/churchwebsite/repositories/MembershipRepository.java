package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Integer> {
}
