package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.dtos.OrganisationDetailDTO;
import com.churchwebsite.churchwebsite.entities.Address;
import com.churchwebsite.churchwebsite.entities.Organisation;
import com.churchwebsite.churchwebsite.services.OrganisationDetailService;
import com.churchwebsite.churchwebsite.services.OrganisationService;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.ObjectNotFoundException;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard/organisation")
public class OrganisationDetailController {

    private final OrganisationDetailService organisationDetailService;
    private final OrganisationService organisationService;

    @Autowired
    public OrganisationDetailController(OrganisationDetailService organisationDetailService,
                                        OrganisationService organisationService) {
        this.organisationDetailService = organisationDetailService;
        this.organisationService = organisationService;
    }


    @GetMapping("/setup")
    public String organizationSetupForm(Model model){

        // Get all organisations in database. Actually there should be a single organisation
//        List<Organisation> organisations = organisationService.findAll();
//        if(!organisations.isEmpty()){
//            // Show organisation already exist page if there is one by setting the activeDashPage model attribute
//            model.addAttribute("activeDashPage", "organisation-exist");
//        }else{
            model.addAttribute("activeDashPage", "organization-setup");
            model.addAttribute("organisationDetail", new OrganisationDetailDTO(new Organisation(), new Address()));
//        }
        return "dashboard/dash-fragments/dash-main-panel";
    }


    @PostMapping("/processSetup")
    public String createOrganisationDetail(@ModelAttribute OrganisationDetailDTO organisationDetail,
                                           @RequestParam("logo") MultipartFile organisationLogo,
                                           @RequestParam("bannerFiles")List<MultipartFile> organisationBanners,
                                           Model model){

        // Call organisation detail service
        organisationDetailService.createOrganisationDetail(organisationDetail, organisationLogo, organisationBanners);
        return "redirect:/dashboard/organisation/detail";
    }



    @GetMapping("/detail")
    public String showOrganisationDetail(@Value("${local.file.banners-dir}") String bannerFilesDir,
                                         Model model,
                                         HttpServletRequest request){
        OrganisationDetailDTO orgDetail = organisationDetailService.getOrganisationDetail();

        String baseBannersPath = File.separator + Paths.get("media/banners") + File.separator;

        if (orgDetail == null){
            model.addAttribute("activeDashPage", "organisation-not-exist");
        }else {
            model.addAttribute("activeDashPage", "organisation-detail");
            model.addAttribute("baseBannersPath", baseBannersPath);
            model.addAttribute("organisationDetail", orgDetail);
        }

        String currentUrl = request.getRequestURI();
        model.addAttribute("currentUrl", currentUrl);

        return "dashboard/dash-fragments/dash-main-panel";
    }


    @GetMapping("/update/{id}")
    public String organizationUpdateForm(Model model, @PathVariable("id") int organisationId){

        Optional<Organisation> org = organisationService.find(organisationId);
        if (org.isEmpty()){
            model.addAttribute("activeDashPage", "organisation-not-exist");
        }else {
            Organisation organisation = org.get();
            model.addAttribute("activeDashPage", "organization-setup");
            model.addAttribute("organisationDetail", new OrganisationDetailDTO(organisation, organisation.getAddress()));
        }

        return "dashboard/dash-fragments/dash-main-panel";
    }

    @PostMapping("/processUpdate")
    public String updateOrganisationDetail(@ModelAttribute OrganisationDetailDTO organisationDetail,
                                           @RequestParam("logo") MultipartFile organisationLogo,
                                           Model model){

        // Call organisation detail service
        organisationDetailService.updateOrganisationDetail(organisationDetail, organisationLogo);
        return "redirect:/dashboard/organisation/detail";
    }


}
