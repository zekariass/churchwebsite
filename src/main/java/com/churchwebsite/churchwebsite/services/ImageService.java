package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.Album;
import com.churchwebsite.churchwebsite.entities.Image;
import com.churchwebsite.churchwebsite.repositories.ImageRepository;
import com.churchwebsite.churchwebsite.services.storage.CloudinaryFileStorageManager;
import com.churchwebsite.churchwebsite.services.storage.LocalFileStorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private LocalFileStorageManager localFileManager;
    private final CloudinaryFileStorageManager cloudinaryFileStorageManager;
    private final AlbumService albumService;

    @Value("${local.file.media-center-dir}")
    private String localImageUploadDir;

    @Value("${cloudinary.file.media-center-dir}")
    private String cloudinaryImageUploadDir;

    @Value("${file.storage.type}")
    private String fileStorageType;

    @Autowired
    public ImageService(ImageRepository imageRepository, CloudinaryFileStorageManager cloudinaryFileStorageManager,
                        AlbumService albumService){
        this.imageRepository = imageRepository;
        this.cloudinaryFileStorageManager = cloudinaryFileStorageManager;
        this.albumService = albumService;
    }

    public List<Image> imageListFor(){
        return imageRepository.findAll();
    }

    public String save(Image image, MultipartFile multipartFile) {
        String savedPath;

        if(fileStorageType.equalsIgnoreCase("cloudinary")){
            Map result = cloudinaryFileStorageManager.storeFile(multipartFile, cloudinaryImageUploadDir);
            savedPath = (String) result.get("url");
            image.setPublicId((String) result.get("public_id"));
        }else{
            localFileManager = new LocalFileStorageManager(localImageUploadDir);
            savedPath = localFileManager.storeFile(multipartFile);
            image.setPublicId(null);
        }

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

    public Page<Image> findByAlbumAndArchived(Boolean archived, int page, Integer pageSize, String sortBy, int albumId) {
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
        return imageRepository.findByAlbumAndArchived(album, archived, pageable);
    }
}
