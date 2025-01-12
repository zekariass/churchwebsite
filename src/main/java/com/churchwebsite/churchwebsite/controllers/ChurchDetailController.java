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
import java.util.Optional;

@Controller
@RequestMapping("/dashboard/church")
public class ChurchDetailController {

    private final ChurchDetailService churchDetailService;
    private final ChurchService churchService;

    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";


    @Autowired
    public ChurchDetailController(ChurchDetailService churchDetailService,
                                  ChurchService churchService) {
        this.churchDetailService = churchDetailService;
        this.churchService = churchService;
    }


    @GetMapping("/setup")
    public String churchSetupForm(Model model){

        ChurchDetailDTO churchDetailDTO = churchDetailService.getChurchDetail() != null
                ? churchDetailService.getChurchDetail()
                : new ChurchDetailDTO(new Church(), new Address()) ;

        model.addAttribute("activeDashPage", "church-setup");
        model.addAttribute("churchDetail", churchDetailDTO);
        model.addAttribute("pageTitle", "Church Setup Form");
        model.addAttribute("showImapField", true);

        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/setup/process")
    public String updateOrganisationDetail(@ModelAttribute ChurchDetailDTO churchDetail,
                                           @RequestParam("logo") MultipartFile churchLogo,
                                           Model model){

        // Call church detail service
        churchDetailService.updateChurchDetail(churchDetail, churchLogo);
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
            model.addAttribute("pageTitle", "Church Not Exist");

        }else {
            model.addAttribute("activeDashPage", "church-detail");
            model.addAttribute("churchDetail", orgDetail);
            model.addAttribute("pageTitle", "Church Detail");

        }

        String currentUrl = request.getRequestURI();
        model.addAttribute("currentUrl", currentUrl);

        return DASHBOARD_MAIN_PANEL;
    }


    @GetMapping("/update/{id}")
    public String churchUpdateForm(Model model, @PathVariable("id") int churchId){

        Optional<Church> org = churchService.find(churchId);
        if (org.isEmpty()){
            model.addAttribute("activeDashPage", "church-not-exist");
            model.addAttribute("pageTitle", "Church Not Exist");

        }else {
            Church church = org.get();
            model.addAttribute("activeDashPage", "church-setup");
            model.addAttribute("churchDetail", new ChurchDetailDTO(church, church.getAddress()));
            model.addAttribute("pageTitle", "Church Setup");

        }

        model.addAttribute("showImapField", true);


        return DASHBOARD_MAIN_PANEL;
    }



}
