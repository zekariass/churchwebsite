package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.Album;
import com.churchwebsite.churchwebsite.entities.Image;
import com.churchwebsite.churchwebsite.repositories.ImageRepository;
import com.churchwebsite.churchwebsite.utils.LocalFileStorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private LocalFileStorageManager localFileManager;
    private final AlbumService albumService;

    @Value("${local.file.media-center-dir}")
    private String uploadDir;

    @Autowired
    public ImageService(ImageRepository imageRepository,
                        AlbumService albumService){
        this.imageRepository = imageRepository;
        this.albumService = albumService;
    }

    public List<Image> imageListFor(){
        return imageRepository.findAll();
    }

    public String save(Image image, MultipartFile multipartFile) {
        localFileManager = new LocalFileStorageManager(uploadDir);
        String savedPath = localFileManager.storeFile(multipartFile);

        image.setImagePath(savedPath);

        imageRepository.save(image);
        return savedPath;
    }

    public Page<Image> getImagesAlbums(int page, Integer pageSize, String sortBy) {
        if(sortBy.isEmpty()){
            sortBy = "creationTime";
        }

        Pageable pageable;
        if(sortBy == "creationTime"){
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.desc(sortBy)));
        }else{
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        }
        return imageRepository.findAll(pageable);
    }

//    public Page<Image> getVideoList(int page, Integer pageSize, String sortBy) {
//        if(sortBy.isEmpty()){
//            sortBy = "uploadTime";
//        }
//
//        Pageable pageable;
//        if(sortBy == "uploadTime"){
//            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.desc(sortBy)));
//        }else{
//            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.asc(sortBy)));
//        }
//        return imageRepository.findByMediaType("Video", pageable);
//    }

    public Page<Image> findByAlbum(int page, Integer pageSize, String sortBy, int albumId) {
        if(sortBy.isEmpty()){
            sortBy = "uploadTime";
        }

        Pageable pageable;
        if(sortBy == "uploadTime"){
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.desc(sortBy)));
        }else{
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        }

        Album album = albumService.getAlbumById(albumId);
        return imageRepository.findByAlbum(album, pageable);
    }
}
