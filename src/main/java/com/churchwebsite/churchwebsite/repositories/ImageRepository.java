package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.Album;
import com.churchwebsite.churchwebsite.entities.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {
//    Page<Image> findByMediaType(String mediaType, Pageable pageable);

    Page<Image> findByAlbum(Album album, Pageable pageable);

    Page<Image> findByAlbumAndArchived(Album album, Boolean archived, Pageable pageable);
}
