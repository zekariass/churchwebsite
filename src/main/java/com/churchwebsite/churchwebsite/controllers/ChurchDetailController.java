package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.dtos.ChurchDetailDTO;
import com.churchwebsite.churchwebsite.entities.Address;
import com.churchwebsite.churchwebsite.entities.Church;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.ChurchService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard/church")
public class ChurchDetailController {

    private final ChurchDetailService churchDetailService;
    private final ChurchService churchService;

    @Autowired
    public ChurchDetailController(ChurchDetailService churchDetailService,
                                  ChurchService churchService) {
        this.churchDetailService = churchDetailService;
        this.churchService = churchService;
    }


    @GetMapping("/setup")
    public String churchSetupForm(Model model){

        // Get all churchs in database. Actually there should be a single church
//        List<Church> churchs = churchService.findAll();
//        if(!churchs.isEmpty()){
//            // Show church already exist page if there is one by setting the activeDashPage model attribute
//            model.addAttribute("activeDashPage", "church-exist");
//        }else{
            model.addAttribute("activeDashPage", "church-setup");
            model.addAttribute("churchDetail", new ChurchDetailDTO(new Church(), new Address()));
//        }
        return "dashboard/dash-fragments/dash-main-panel";
    }


    @PostMapping("/processSetup")
    public String createOrganisationDetail(@ModelAttribute ChurchDetailDTO churchDetail,
                                           @RequestParam("logo") MultipartFile churchLogo,
                                           @RequestParam("bannerFiles")List<MultipartFile> churchBanners,
                                           Model model){

        // Call church detail service
        churchDetailService.createChurchDetail(churchDetail, churchLogo, churchBanners);
        return "redirect:/dashboard/church/detail";
    }



    @GetMapping("/detail")
    public String showOrganisationDetail(@Value("${local.file.banners-dir}") String bannerFilesDir,
                                         Model model,
                                         HttpServletRequest request){
        ChurchDetailDTO orgDetail = churchDetailService.getChurchDetail();

        String baseBannersPath = File.separator + Paths.get("media/banners") + File.separator;

        if (orgDetail == null){
            model.addAttribute("activeDashPage", "church-not-exist");
        }else {
            model.addAttribute("activeDashPage", "church-detail");
//            model.addAttribute("baseBannersPath", baseBannersPath);
            model.addAttribute("churchDetail", orgDetail);
        }

        String currentUrl = request.getRequestURI();
        model.addAttribute("currentUrl", currentUrl);

        return "dashboard/dash-fragments/dash-main-panel";
    }


    @GetMapping("/update/{id}")
    public String churchUpdateForm(Model model, @PathVariable("id") int churchId){

        Optional<Church> org = churchService.find(churchId);
        if (org.isEmpty()){
            model.addAttribute("activeDashPage", "church-not-exist");
        }else {
            Church church = org.get();
            model.addAttribute("activeDashPage", "church-setup");
            model.addAttribute("churchDetail", new ChurchDetailDTO(church, church.getAddress()));
        }

        return "dashboard/dash-fragments/dash-main-panel";
    }

    @PostMapping("/processUpdate")
    public String updateOrganisationDetail(@ModelAttribute ChurchDetailDTO churchDetail,
                                           @RequestParam("logo") MultipartFile churchLogo,
                                           Model model){

        // Call church detail service
        churchDetailService.updateChurchDetail(churchDetail, churchLogo);
        return "redirect:/dashboard/church/detail";
    }


}
