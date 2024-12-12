package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.ChurchBanner;
import com.churchwebsite.churchwebsite.services.ChurchBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/dashboard/church/banner")
public class ChurchBannerController {

    private final ChurchBannerService churchBannerService;

    @Autowired
    public ChurchBannerController(ChurchBannerService churchBannerService) {
        this.churchBannerService = churchBannerService;
    }

    @GetMapping("/add")
    public String showBannerForm(@RequestParam(value = "churchId", required = false) int orgId,
//                                 HttpServletRequest request,
                                 Model model){

        model.addAttribute("activeDashPage", "churchBanner");
        model.addAttribute("churchBanner", new ChurchBanner());
        model.addAttribute("churchId", orgId);
//        model.addAttribute("currentUrl", request.getRequestURL());

        return "dashboard/dash-fragments/dash-main-panel";
    }

    @PostMapping("/processAddBanner")
    public String processAddBanner(@RequestParam(value = "returnUrl", required = false) final String returnUrl,
                                       @RequestParam(value = "churchId", required = false) Integer orgId,
                                       @RequestParam(value = "bannerFiles") List<MultipartFile> multipartFile){

        List<ChurchBanner> banner = churchBannerService.saveAll(multipartFile, orgId);
        if(banner == null){
            return "redirect:/dashboard/home";
        }
        return "redirect:"+(returnUrl != null ? returnUrl : "/dashboard/home");
    }


    @GetMapping("/delete/{id}")
    public String deleteBanner(@PathVariable(value = "id", required = true) int bannerId,
                               @RequestHeader(value = "Referer", required = false) String referer){

        churchBannerService.delete(bannerId);

        return "redirect:"+referer;
    }

}
