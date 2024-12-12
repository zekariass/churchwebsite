package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.Church;
import com.churchwebsite.churchwebsite.entities.ChurchBanner;
import com.churchwebsite.churchwebsite.repositories.ChurchBannerRepository;
import com.churchwebsite.churchwebsite.utils.LocalFileStorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChurchBannerService {

    private final ChurchBannerRepository churchBannerRepository;
    private LocalFileStorageManager fileStorageManager;
    private final ChurchService churchService;

    @Value("${local.file.banners-dir}")
    private String bannerFilesDir;

    @Autowired
    public ChurchBannerService(ChurchBannerRepository churchBannerRepository,
                               ChurchService churchService) {
        this.churchBannerRepository = churchBannerRepository;
        this.churchService = churchService;

    }

    public List<ChurchBanner> saveAll(List<MultipartFile> multipartFiles, Integer orgId){
        fileStorageManager = new LocalFileStorageManager(bannerFilesDir);

        Optional<Church> church = churchService.find(orgId);
        if(church.isEmpty()){
            return null;
        }

        List<ChurchBanner> banners = new ArrayList<>();
        for(MultipartFile file: multipartFiles){
            String bannerPath = fileStorageManager.storeFile(file);
            banners.add(new ChurchBanner(bannerPath, church.get()));
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
        LocalFileStorageManager storageManager = new LocalFileStorageManager(bannerFilesDir);
        storageManager.deleteFile(banner.getChurchBanner());
        churchBannerRepository.delete(banner);
    }
}
