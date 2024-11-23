package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.Media;
import com.churchwebsite.churchwebsite.repositories.MediaRepository;
import com.churchwebsite.churchwebsite.utils.LocalFileStorageManager;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class MediaService {
    private final MediaRepository mediaRepository;
    private LocalFileStorageManager localFileManager;

    @Value("${local.file.media-center-dir}")
    private String uploadDir;

    @Autowired
    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    public List<Media> mediaListFor(){
        return mediaRepository.findAll();
    }

    public String save(Media media, MultipartFile multipartFile) {
        localFileManager = new LocalFileStorageManager(uploadDir);
        String savedPath = localFileManager.storeFile(multipartFile);

        media.setMediaPath(savedPath);

        mediaRepository.save(media);
        return savedPath;
    }
}
