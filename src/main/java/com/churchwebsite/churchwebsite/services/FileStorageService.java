package com.churchwebsite.churchwebsite.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageService {
    String storeFile(MultipartFile file);
    Resource loadFileAsResource(String filename);
    void deleteFile(String filename);
    List<String> listFiles();
}
