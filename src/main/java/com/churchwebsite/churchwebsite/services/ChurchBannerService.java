package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.Church;
import com.churchwebsite.churchwebsite.entities.ChurchBanner;
import com.churchwebsite.churchwebsite.repositories.ChurchBannerRepository;
import com.churchwebsite.churchwebsite.services.storage.CloudinaryFileStorageManager;
import com.churchwebsite.churchwebsite.services.storage.LocalFileStorageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ChurchBannerService {

    private final Logger logger = LoggerFactory.getLogger(ChurchBannerService.class);

    private final ChurchBannerRepository churchBannerRepository;
    private LocalFileStorageManager fileStorageManager;
    private final ChurchService churchService;
    private final CloudinaryFileStorageManager cloudinaryFileStorageManager;

    @Value("${local.file.banners-dir}")
    private String localBannerFilesDir;

    @Value("${cloudinary.file.banners-dir}")
    private String cloudinaryBannerFilesDir;

    @Value("${file.storage.type}")
    private String fileStorageType;

    @Autowired
    public ChurchBannerService(ChurchBannerRepository churchBannerRepository,
                               ChurchService churchService, CloudinaryFileStorageManager cloudinaryFileStorageManager) {
        this.churchBannerRepository = churchBannerRepository;
        this.churchService = churchService;

        this.cloudinaryFileStorageManager = cloudinaryFileStorageManager;
    }

    public List<ChurchBanner> saveAll(List<MultipartFile> multipartFiles, Integer orgId){

        Optional<Church> church = churchService.find(orgId);
        if(church.isEmpty()){
            return null;
        }

            List<ChurchBanner> banners = new ArrayList<>();

            if(fileStorageType.equalsIgnoreCase("cloudinary")){
                for(MultipartFile file: multipartFiles) {
                    Map result = cloudinaryFileStorageManager.storeFile(file, cloudinaryBannerFilesDir);
                    System.out.println("=================================: "+result);

                    banners.add(new ChurchBanner((String) result.get("secure_url"), (String) result.get("public_id"), church.get()));
                }
            }else{
                fileStorageManager = new LocalFileStorageManager(localBannerFilesDir);
                for(MultipartFile file: multipartFiles) {
                    String bannerPath = fileStorageManager.storeFile(file);
                    banners.add(new ChurchBanner(bannerPath, null, church.get()));
                }
            }


        return churchBannerRepository.saveAll(banners);
    }


    public ChurchBanner save(ChurchBanner banner){
        return churchBannerRepository.save(banner);
    }

    public void delete(int bannerId) {

        ChurchBanner banner = churchBannerRepository.findById(bannerId).orElseThrow(
                ()-> new RuntimeException("Banner object is not found!")
        );
        if(fileStorageType.equalsIgnoreCase("cloudinary")){
            try {
                cloudinaryFileStorageManager.deleteFile(banner.getPublicId());
                churchBannerRepository.delete(banner);

            } catch (IOException e) {
                logger.error("ERROR ======================: {}", e.getMessage());
            }
        }else{
            LocalFileStorageManager storageManager = new LocalFileStorageManager(localBannerFilesDir);
            storageManager.deleteFile(banner.getChurchBanner());
            churchBannerRepository.delete(banner);
        }

    }
}
