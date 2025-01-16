package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.dtos.ChurchDetailDTO;
import com.churchwebsite.churchwebsite.entities.Address;
import com.churchwebsite.churchwebsite.entities.Church;
import com.churchwebsite.churchwebsite.entities.ChurchBanner;
import com.churchwebsite.churchwebsite.services.storage.CloudinaryFileStorageManager;
import com.churchwebsite.churchwebsite.services.storage.LocalFileStorageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class ChurchDetailService {
    private final ChurchService churchService;
    private final CloudinaryFileStorageManager cloudinaryFileStorageManager;

    private final Logger logger = LoggerFactory.getLogger(ChurchDetailService.class);

    @Value("${local.file.logo-dir}")
    private String localLogoFileDir;

    @Value("${cloudinary.file.logo-dir}")
    private String cloudinaryLogoFileDir;

    @Value("${local.file.banners-dir}")
    private String localBannerFilesDir;

    @Value("${cloudinary.file.banners-dir}")
    private String cloudinaryBannerFilesDir;

    @Value("${file.storage.type}")
    private String fileStorageType;


    @Autowired
    public ChurchDetailService(ChurchService churchService, CloudinaryFileStorageManager cloudinaryFileStorageManager) {
        this.churchService = churchService;
        this.cloudinaryFileStorageManager = cloudinaryFileStorageManager;
    }

    public ChurchDetailDTO createChurchDetail(ChurchDetailDTO orgDetailDTO,
                                                    MultipartFile churchLogo,
                                                    List<MultipartFile> churchBanners) {

        Church church = orgDetailDTO.getChurch();

        if(fileStorageType.equalsIgnoreCase("cloudinary")){

            Map logoResult = cloudinaryFileStorageManager.storeFile(churchLogo, cloudinaryLogoFileDir);
            church.setChurchLogo((String) logoResult.get("secure_url"));
            church.setPublicId((String) logoResult.get("public_id"));

            for(MultipartFile file: churchBanners){
                Map bannerResult = cloudinaryFileStorageManager.storeFile(file, cloudinaryBannerFilesDir);
                ChurchBanner banner = new ChurchBanner((String) bannerResult.get("secure_url"), (String) bannerResult.get("public_id"), church);
                orgDetailDTO.addBanner(banner);
            }

        }else{
            // Instantiate local file managers
            LocalFileStorageManager logoFileStorageManager = new LocalFileStorageManager(this.localLogoFileDir);
            LocalFileStorageManager bannerFileStorageManager = new LocalFileStorageManager(this.localBannerFilesDir);
            String logoPath = logoFileStorageManager.storeFile(churchLogo);
            church.setChurchLogo(logoPath);

            for (MultipartFile file : churchBanners) {
                String bannerPath = bannerFileStorageManager.storeFile(file);
                ChurchBanner banner = new ChurchBanner(bannerPath, null, church);
                orgDetailDTO.addBanner(banner);
            }
        }


        church.setBanners(orgDetailDTO.getBanners());
        Address churchAddress = orgDetailDTO.getAddress();
        church.setAddress(churchAddress);

        churchService.save(church);

        return orgDetailDTO;
    }


    public ChurchDetailDTO getChurchDetail(){
        List<Church> churches = churchService.findAll();
        if(churches.isEmpty()){
            return null;
        }
        Church church = churches.getFirst();
        church.setChurchLogo(church.getChurchLogo());
        Address address = church.getAddress();
        List<ChurchBanner> banners = church.getBanners();

        return new ChurchDetailDTO(church, banners, address);
    }


    public ChurchDetailDTO updateChurchDetail(ChurchDetailDTO orgDetailDTO,
                                                    MultipartFile churchLogo) {

        Church church = orgDetailDTO.getChurch();

        // Instantiate local file managers
        if(churchLogo.isEmpty()){
            churchService.find(church.getChurchId()).ifPresent(existingChurch -> church.setChurchLogo(existingChurch.getChurchLogo()));
        }else{

            // Save to cloudinary
            if(fileStorageType.equalsIgnoreCase("cloudinary")){
                Map result = cloudinaryFileStorageManager.storeFile(churchLogo, cloudinaryLogoFileDir);
                church.setChurchLogo((String) result.get("secure_url"));
                church.setPublicId((String) result.get("public_id"));
            }else{
                // Save to local file
                LocalFileStorageManager logoFileStorageManager = new LocalFileStorageManager(localLogoFileDir);
                String logoPath = logoFileStorageManager.storeFile(churchLogo);
                church.setChurchLogo(logoPath);
            }

        }

        church.setBanners(church.getBanners());

        Address churchAddress = orgDetailDTO.getAddress();
        church.setAddress(churchAddress);

        churchService.save(church);

        return orgDetailDTO;
    }

}
