package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Integer> {
//    Page<Album> findAll(Pageable pageable);
    Album findByAlbumName(String blog);

//    // Fetch albums containing only images
//    @Query("SELECT a FROM Album a WHERE a.mediaList IS NOT EMPTY AND " +
//            "SIZE(a.mediaList) = (SELECT COUNT(m) FROM Image m WHERE m.album = a AND m.mediaType = 'Image')")
//    Page<Album> findAlbumsWithOnlyImages();
//
//    // Fetch albums containing only videos
//    @Query("SELECT a FROM Album a WHERE a.mediaList IS NOT EMPTY AND " +
//            "SIZE(a.mediaList) = (SELECT COUNT(m) FROM Image m WHERE m.album = a AND m.mediaType = 'Video')")
//    Page<Album> findAlbumsWithOnlyVideos();
}
