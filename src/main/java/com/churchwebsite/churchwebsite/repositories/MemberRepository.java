package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}
