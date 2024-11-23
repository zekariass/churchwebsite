package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.dtos.OrganisationDetailDTO;
import com.churchwebsite.churchwebsite.entities.Organisation;
import com.churchwebsite.churchwebsite.entities.OrganisationBanner;
import com.churchwebsite.churchwebsite.services.OrganisationBannerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/dashboard/organisation/banner")
public class OrganisationBannerController {

    private final OrganisationBannerService organisationBannerService;

    @Autowired
    public OrganisationBannerController(OrganisationBannerService organisationBannerService) {
        this.organisationBannerService = organisationBannerService;
    }

    @GetMapping("/add")
    public String showBannerForm(@RequestParam(value = "organisationId", required = false) int orgId,
//                                 HttpServletRequest request,
                                 Model model){

        model.addAttribute("activeDashPage", "organisationBanner");
        model.addAttribute("organisationBanner", new OrganisationBanner());
        model.addAttribute("organisationId", orgId);
//        model.addAttribute("currentUrl", request.getRequestURL());

        return "dashboard/dash-fragments/dash-main-panel";
    }

    @PostMapping("/processAddBanner")
    public String processAddBanner(@RequestParam(value = "returnUrl", required = false) final String returnUrl,
                                       @RequestParam(value = "organisationId", required = false) Integer orgId,
                                       @RequestParam(value = "bannerFiles") List<MultipartFile> multipartFile){

        List<OrganisationBanner> banner = organisationBannerService.saveAll(multipartFile, orgId);
        if(banner == null){
            return "redirect:/dashboard/home";
        }
        return "redirect:"+(returnUrl != null ? returnUrl : "/dashboard/home");
    }


    @GetMapping("/delete/{id}")
    public String deleteBanner(@PathVariable(value = "id", required = true) int bannerId,
                               @RequestHeader(value = "Referer", required = false) String referer){

        organisationBannerService.delete(bannerId);

        return "redirect:"+referer;
    }

}
