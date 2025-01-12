package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.AboutUs;
import com.churchwebsite.churchwebsite.services.AboutUsService;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.PaginationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dashboard/aboutUs")
public class AboutUsController {

    private final AboutUsService aboutUsService;
    private final PaginationService paginationService;
    private final ChurchDetailService churchDetailService;


    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";

    @Autowired
    public AboutUsController(AboutUsService aboutUsService, PaginationService paginationService, ChurchDetailService churchDetailService) {
        this.aboutUsService = aboutUsService;
        this.paginationService = paginationService;
        this.churchDetailService = churchDetailService;
    }

    @GetMapping("/list")
    public String getAllAboutUs(Model model,
                              @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                              @RequestParam(value = "size", required = false) Integer pageSize,
                              @RequestParam(value = "sortBy", defaultValue = "lastModifiedAt") String sortBy,
                              HttpServletRequest request){

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<AboutUs> pagedAboutUs = aboutUsService.getAllAboutUs(page, pageSize, sortBy);
        List<AboutUs> aboutUsList = pagedAboutUs.getContent();

        model.addAttribute("activeDashPage", "about-us-list");
        model.addAttribute("aboutUsList", aboutUsList);

        model.addAttribute("currentPage", pagedAboutUs.getNumber()+1);
        model.addAttribute("totalItems", pagedAboutUs.getTotalElements());
        model.addAttribute("totalPages", pagedAboutUs.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("sortBy", sortBy);

        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "About Us List");


        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/detail/{id}")
    public String getAboutUsById(@PathVariable Integer id, Model model) {
        model.addAttribute("activeDashPage", "about-us-detail");

        AboutUs aboutUs = aboutUsService.getAboutUsById(id);
        model.addAttribute("aboutUs", aboutUs);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "About Us Detail");

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/form")
    public String showAboutUsForm(Model model) {
        model.addAttribute("activeDashPage", "about-us-form");
        model.addAttribute("aboutUs", new AboutUs());
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "About Us Form");

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/edit/{id}")
    public String showAboutUsFormForUpdate(@PathVariable("id") int anId, Model model) {
        model.addAttribute("activeDashPage", "about-us-form");
        model.addAttribute("aboutUs", aboutUsService.getAboutUsById(anId));
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "About Us Form");

        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/form/process")
    public String processAboutUsForm(@ModelAttribute AboutUs aboutUs) {
        aboutUsService.saveAboutUs(aboutUs);
        return "redirect:/dashboard/aboutUs/list";
    }


    @GetMapping("/delete/{id}")
    public String deleteAboutUs(@PathVariable Integer id) {
        aboutUsService.deleteAboutUs(id);
        return "redirect:/dashboard/aboutUs/list";
    }
}
