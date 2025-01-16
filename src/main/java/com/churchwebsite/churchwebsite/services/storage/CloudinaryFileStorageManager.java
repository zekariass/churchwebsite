package com.churchwebsite.churchwebsite.services.storage;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

@Component
public class CloudinaryFileStorageManager {

    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryFileStorageManager(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public Map storeFile(MultipartFile file, String folder) {
        Map storageParams = storageParams = ObjectUtils.asMap(
                "use_filename", true,
                "unique_filename", true,
                "overwrite", true,
                "secure", true,
                "folder", folder
        );
        Map uploadResult;
        try {
           uploadResult =  cloudinary.uploader().upload(file.getBytes(), storageParams);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return uploadResult;
    }


    public Map storeRawFile(MultipartFile file, String folder) {

        String filename = file.getOriginalFilename();
        String extension = filename != null ? filename.substring(filename.lastIndexOf('.')) : "";

        // Explicitly set the public_id with the filename and extension
        String publicId = folder + "/" + UUID.randomUUID()+'.'+extension;

        // Set upload parameters with explicit public_id to keep the extension
        Map<String, Object> uploadParams = ObjectUtils.asMap(
                "resource_type", "raw",  // Keep resource_type as 'raw' for non-image files
                "public_id", publicId,   // Ensure filename with extension is set
                "use_filename", true,
                "unique_filename", true,
                "overwrite", true,
                "secure", true
//                "folder", folder// Use HTTPS
        );

        Map uploadResult;
        try {
            uploadResult =  cloudinary.uploader().upload(file.getBytes(), uploadParams);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return uploadResult;
    }


    public InputStream downloadFileAsResource(String publicId) throws Exception {
//        ApiResponse result = cloudinary.api().resource(publicId, ObjectUtils.emptyMap());
//        String url = (String) result.get("secure_url");
//        return new URL(url).openStream();

        String pdfUrl = cloudinary.url()
                .resourceType("raw")
                .publicId(publicId)
                .secure(true)
                .signed(true)  // Ensure the URL is signed
                .generate();

        return new URL(pdfUrl).openStream();

    }

    public void deleteFile(String publicId) throws IOException {

        cloudinary.uploader().destroy(publicId, ObjectUtils.asMap("invalidate", true));
    }

}
