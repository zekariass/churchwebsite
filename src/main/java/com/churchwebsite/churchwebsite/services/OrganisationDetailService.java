package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.dtos.OrganisationDetailDTO;
import com.churchwebsite.churchwebsite.entities.Address;
import com.churchwebsite.churchwebsite.entities.Organisation;
import com.churchwebsite.churchwebsite.entities.OrganisationBanner;
import com.churchwebsite.churchwebsite.utils.LocalFileStorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrganisationDetailService {
    private final OrganisationService organisationService;

    @Value("${local.file.logo-dir}")
    private String logoFileDir;

    @Value("${local.file.banners-dir}")
    private String bannerFilesDir;


    @Autowired
    public OrganisationDetailService(OrganisationService organisationService) {
        this.organisationService = organisationService;
    }

//    public OrganisationDetailDTO createOrganisationDetail(OrganisationDetailDTO orgDetailDTO,
//                                                          MultipartFile organisationLogo,
//                                                          List<MultipartFile> organisationBanners) {
//
//        // Instantiate local file managers
//        LocalFileStorageManager logoFileStorageManager = new LocalFileStorageManager(this.logoFileDir);
//        LocalFileStorageManager bannerFileStorageManager = new LocalFileStorageManager(this.bannerFilesDir);
//
//        String logoPath = logoFileStorageManager.storeFile(organisationLogo);
//        orgDetailDTO.setOrganisationLogo(logoPath);
//
//
//        for (MultipartFile file : organisationBanners) {
//            String bannerPath = bannerFileStorageManager.storeFile(file);
//            orgDetailDTO.addBanner(bannerPath);
//        }
//
//        Address organisationAddress = orgDetailDTO.getAddress();
//
//        Organisation organisation = orgDetailDTO.getOrgnisation();
//
//        List<OrganisationBanner> banners = orgDetailDTO.getBanners();
//
//        organisation.setBanners(banners);
//        Organisation savedOrganisation = organisationService.save(organisation);
//
//        return orgDetailDTO;
//    }

    public OrganisationDetailDTO createOrganisationDetail(OrganisationDetailDTO orgDetailDTO,
                                                          MultipartFile organisationLogo,
                                                          List<MultipartFile> organisationBanners) {
                // Instantiate local file managers
        LocalFileStorageManager logoFileStorageManager = new LocalFileStorageManager(this.logoFileDir);
        LocalFileStorageManager bannerFileStorageManager = new LocalFileStorageManager(this.bannerFilesDir);

        String logoPath = logoFileStorageManager.storeFile(organisationLogo);
        Organisation organisation = orgDetailDTO.getOrganisation();
        organisation.setOrganisationLogo(logoPath);

        for (MultipartFile file : organisationBanners) {
            String bannerPath = bannerFileStorageManager.storeFile(file);
            OrganisationBanner banner = new OrganisationBanner(bannerPath, organisation);
            orgDetailDTO.addBanner(banner);
        }

        organisation.setBanners(orgDetailDTO.getBanners());

        Address organisationAddress = orgDetailDTO.getAddress();
        organisation.setAddress(organisationAddress);

        organisationService.save(organisation);

        return orgDetailDTO;
    }


    public OrganisationDetailDTO getOrganisationDetail(){
        List<Organisation> organisations = organisationService.findAll();
        if(organisations.isEmpty()){
            return null;
        }
        Organisation organisation = organisations.getFirst();
        organisation.setOrganisationLogo(File.separator + Paths.get("media/logo") + File.separator + organisation.getOrganisationLogo());
        Address address = organisation.getAddress();
        List<OrganisationBanner> banners = organisation.getBanners();

        OrganisationDetailDTO orgDetail = new OrganisationDetailDTO(organisation, banners, address);

//        List<String> orgBanners = new ArrayList<>();
//        for(String banner: orgDetail.getOrganisationBanners()){
//            orgBanners.add(File.separator + Paths.get("media/banners") + File.separator + banner);
//        }
//
//        orgDetail.setOrganisationBanners(orgBanners);

        return orgDetail;
    }


    public OrganisationDetailDTO updateOrganisationDetail(OrganisationDetailDTO orgDetailDTO,
                                                          MultipartFile organisationLogo) {

        Organisation organisation = orgDetailDTO.getOrganisation();

        // Instantiate local file managers
        if(organisationLogo.isEmpty()){
            organisation.setOrganisationLogo(organisation.getOrganisationLogo());
        }else{
            LocalFileStorageManager logoFileStorageManager = new LocalFileStorageManager(this.logoFileDir);
            String logoPath = logoFileStorageManager.storeFile(organisationLogo);
            organisation.setOrganisationLogo(logoPath);
        }

        organisation.setBanners(organisation.getBanners());

        System.out.println("ORGANISATION: ===============================>>> "+ organisation);

        Address organisationAddress = orgDetailDTO.getAddress();
        organisation.setAddress(organisationAddress);

        organisationService.save(organisation);

        return orgDetailDTO;
    }

}
