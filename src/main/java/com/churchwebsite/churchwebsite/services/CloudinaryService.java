//package com.churchwebsite.churchwebsite.services;
//
//import com.cloudinary.Cloudinary;
//import com.cloudinary.utils.ObjectUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.Map;
//
//@Service
//public class CloudinaryService {
//
//    private final Cloudinary cloudinary;
//    Map params1;
//
//    @Autowired
//    public CloudinaryService(Cloudinary cloudinary) {
//        this.cloudinary = cloudinary;
//        params1 = ObjectUtils.asMap(
//                "use_filename", false,
//                "unique_filename", true,
//                "overwrite", true
//        );
//    }
//
//    public Map uploadToCloud(MultipartFile multipartFile, String folder) throws IOException {
//        params1.put("folder", folder);
//        return cloudinary.uploader().upload(multipartFile.getBytes(), params1);
//    }
//}
