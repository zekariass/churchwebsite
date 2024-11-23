package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.Organisation;
import com.churchwebsite.churchwebsite.entities.OrganisationBanner;
import com.churchwebsite.churchwebsite.repositories.OrganisationBannerRepository;
import com.churchwebsite.churchwebsite.utils.LocalFileStorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrganisationBannerService {

    private final OrganisationBannerRepository organisationBannerRepository;
    private LocalFileStorageManager fileStorageManager;
    private final OrganisationService organisationService;

    @Value("${local.file.banners-dir}")
    private String bannerFilesDir;

    @Autowired
    public OrganisationBannerService(OrganisationBannerRepository organisationBannerRepository,
                                     OrganisationService organisationService) {
        this.organisationBannerRepository = organisationBannerRepository;
        this.organisationService = organisationService;

    }

    public List<OrganisationBanner> saveAll(List<MultipartFile> multipartFiles, Integer orgId){
        fileStorageManager = new LocalFileStorageManager(bannerFilesDir);

        Optional<Organisation> organisation = organisationService.find(orgId);
        if(organisation.isEmpty()){
            return null;
        }

        List<OrganisationBanner> banners = new ArrayList<>();
        for(MultipartFile file: multipartFiles){
            String bannerPath = fileStorageManager.storeFile(file);
            banners.add(new OrganisationBanner(bannerPath, organisation.get()));
        }

        return organisationBannerRepository.saveAll(banners);
    }

    public OrganisationBanner save(OrganisationBanner banner){
        return organisationBannerRepository.save(banner);
    }

    public void delete(int bannerId) {

        OrganisationBanner banner = organisationBannerRepository.findById(bannerId).orElseThrow(
                ()-> new RuntimeException("Banner object is not found!")
        );
        LocalFileStorageManager storageManager = new LocalFileStorageManager(bannerFilesDir);
        storageManager.deleteFile(banner.getOrganisationBanner());
        organisationBannerRepository.delete(banner);
    }
}
