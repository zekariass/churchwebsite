package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
