package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.MembershipCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipCategoryRepository extends JpaRepository<MembershipCategory, Integer> {
}
