package com.churchwebsite.churchwebsite.utils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class LocalFileStorageManager {

    // File storage location
    private final Path fileStorageLocation;

    public LocalFileStorageManager(String storageDir) {

        // Change String file name to standard Path object
        this.fileStorageLocation = Paths.get(storageDir).toAbsolutePath().normalize();

        try {
            // Create directory if not exist
            Files.createDirectories(this.fileStorageLocation);
        }catch (IOException exc){
            throw new RuntimeException("Unable to create the file directory. ", exc);
        }
    }


    public String storeFile(MultipartFile file){

        // Prepare the filename
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // Resolve the filename
        Path targetLocation = this.resolveFilename(filename);
        try{
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return filename;
        }catch (IOException exc){
            throw new RuntimeException("Unable to copy the file from filestream.");
        }

    }

    public Resource loadFileAsResource(String filename){

        // Resolve the filename
        Path fileLocation  = this.resolveFilename(filename);

        try {

            // Create the downloadable resource
            Resource resource = new UrlResource(fileLocation.toUri());
            if (resource.exists()){
                return resource;
            }else{
                throw new RuntimeException("File not found: "+ filename);
            }
        }catch (IOException exc){
            throw new RuntimeException("File not found: "+ filename, exc);
        }
    }

    public void deleteFile(String fileName){
       try{
        Path filePath = this.resolveFilename(fileName);
        Files.deleteIfExists(filePath);
    }catch (IOException e){
        throw new RuntimeException("Could not delete file "+ fileName + ". Please try again!", e);
    }

    }

    public Path resolveFilename(String filename){
        return this.fileStorageLocation.resolve(filename).normalize();
    }

}