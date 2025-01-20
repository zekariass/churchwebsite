package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.Album;
import com.churchwebsite.churchwebsite.repositories.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;

    @Autowired
    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public Page<Album> getAlbumListByArchived(int page, int pageSize, String sortBy, Boolean archived){

        Pageable pageable;
        if(sortBy.equals("creationTime")){
            pageable = PageRequest.of(Math.max(page - 1, 0), pageSize, Sort.by(Sort.Order.desc(sortBy)));
        }else {
            pageable = PageRequest.of(Math.max(page - 1, 0), pageSize, Sort.by(Sort.Order.asc(sortBy)));
        }
        return albumRepository.findByArchived(archived, pageable);
    }

    public Page<Album> getAlbumList(int page, int pageSize, String sortBy){

        Pageable pageable;
        if(sortBy.equals("creationTime")){
            pageable = PageRequest.of(Math.max(page - 1, 0), pageSize, Sort.by(Sort.Order.desc(sortBy)));
        }else {
            pageable = PageRequest.of(Math.max(page - 1, 0), pageSize, Sort.by(Sort.Order.asc(sortBy)));
        }
        return albumRepository.findAll(pageable);
    }

    public List<Album> getAlbumList(){
        return albumRepository.findAll();
    }

    public Album save(Album album) {

        return albumRepository.save(album);
    }

    public Album getAlbumById(int albumId) {
        return albumRepository.findById(albumId).orElseThrow(
                ()->new RuntimeException("No album found!")
        );
    }

    public Album findAlbumByAlbumName(String blog) {

       return albumRepository.findByAlbumName(blog);
    }

    public void deleteById(int albumId) {
        albumRepository.deleteById(albumId);
    }
}
