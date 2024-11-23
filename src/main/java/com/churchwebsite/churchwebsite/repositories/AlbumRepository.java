package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Integer> {
}
