package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    Page<Event> findAll(Pageable pageable);
}
