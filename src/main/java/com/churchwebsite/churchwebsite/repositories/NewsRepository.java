package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Integer> {
}
