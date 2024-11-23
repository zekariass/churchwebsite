package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
}
