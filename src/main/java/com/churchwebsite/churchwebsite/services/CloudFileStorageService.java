package com.churchwebsite.churchwebsite.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class CloudFileStorageService implements FileStorageService{
    @Override
    public String storeFile(MultipartFile file) {
        return "";
    }

    @Override
    public Resource loadFileAsResource(String filename) {
        return null;
    }

    @Override
    public void deleteFile(String filename) {

    }

    @Override
    public List<String> listFiles() {
        return List.of();
    }
}
