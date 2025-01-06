package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.dtos.ChurchDetailDTO;
import com.churchwebsite.churchwebsite.entities.Address;
import com.churchwebsite.churchwebsite.entities.Church;
import com.churchwebsite.churchwebsite.entities.ChurchBanner;
import com.churchwebsite.churchwebsite.utils.LocalFileStorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ChurchDetailService {
    private final ChurchService churchService;

    @Value("${local.file.logo-dir}")
    private String logoFileDir;

    @Value("${local.file.banners-dir}")
    private String bannerFilesDir;


    @Autowired
    public ChurchDetailService(ChurchService churchService) {
        this.churchService = churchService;
    }

//    public ChurchDetailDTO createChurchDetail(ChurchDetailDTO orgDetailDTO,
//                                                          MultipartFile churchLogo,
//                                                          List<MultipartFile> churchBanners) {
//
//        // Instantiate local file managers
//        LocalFileStorageManager logoFileStorageManager = new LocalFileStorageManager(this.logoFileDir);
//        LocalFileStorageManager bannerFileStorageManager = new LocalFileStorageManager(this.bannerFilesDir);
//
//        String logoPath = logoFileStorageManager.storeFile(churchLogo);
//        orgDetailDTO.setChurchLogo(logoPath);
//
//
//        for (MultipartFile file : churchBanners) {
//            String bannerPath = bannerFileStorageManager.storeFile(file);
//            orgDetailDTO.addBanner(bannerPath);
//        }
//
//        Address churchAddress = orgDetailDTO.getAddress();
//
//        Church church = orgDetailDTO.getOrgnisation();
//
//        List<ChurchBanner> banners = orgDetailDTO.getBanners();
//
//        church.setBanners(banners);
//        Church savedChurch = churchService.save(church);
//
//        return orgDetailDTO;
//    }

    public ChurchDetailDTO createChurchDetail(ChurchDetailDTO orgDetailDTO,
                                                    MultipartFile churchLogo,
                                                    List<MultipartFile> churchBanners) {
                // Instantiate local file managers
        LocalFileStorageManager logoFileStorageManager = new LocalFileStorageManager(this.logoFileDir);
        LocalFileStorageManager bannerFileStorageManager = new LocalFileStorageManager(this.bannerFilesDir);

        String logoPath = logoFileStorageManager.storeFile(churchLogo);
        Church church = orgDetailDTO.getChurch();
        church.setChurchLogo(logoPath);

        for (MultipartFile file : churchBanners) {
            String bannerPath = bannerFileStorageManager.storeFile(file);
            ChurchBanner banner = new ChurchBanner(bannerPath, church);
            orgDetailDTO.addBanner(banner);
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
            church.setChurchLogo(church.getChurchLogo());
        }else{
            LocalFileStorageManager logoFileStorageManager = new LocalFileStorageManager(this.logoFileDir);
            String logoPath = logoFileStorageManager.storeFile(churchLogo);
            church.setChurchLogo(logoPath);
        }

        church.setBanners(church.getBanners());

//        System.out.println("Church: ===============================>>> "+ church);

        Address churchAddress = orgDetailDTO.getAddress();
        church.setAddress(churchAddress);

        churchService.save(church);

        return orgDetailDTO;
    }

}
