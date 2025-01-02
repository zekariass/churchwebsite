package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.Announcement;
import com.churchwebsite.churchwebsite.services.AnnouncementService;
import com.churchwebsite.churchwebsite.services.PaginationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dashboard/announcements")
public class AnnouncementController {


    private final AnnouncementService announcementService;
    private final PaginationService paginationService;

    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";

    @Autowired
    public AnnouncementController(AnnouncementService announcementService, PaginationService paginationService) {
        this.announcementService = announcementService;
        this.paginationService = paginationService;
    }

    @GetMapping
    public String getAllAnnouncements(Model model,
                                      @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                      @RequestParam(value = "size", required = false) Integer pageSize,
                                      @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                                      HttpServletRequest request){

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<Announcement> pagedAnnouncement = announcementService.getAllShipments(page, pageSize, sortBy);
        List<Announcement> announcements = pagedAnnouncement.getContent();

        model.addAttribute("activeDashPage", "announcements-list");
        model.addAttribute("announcements", announcements);

        model.addAttribute("currentPage", pagedAnnouncement.getNumber()+1);
        model.addAttribute("totalItems", pagedAnnouncement.getTotalElements());
        model.addAttribute("totalPages", pagedAnnouncement.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("sortBy", sortBy);

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/detail/{id}")
    public String getAnnouncementById(@PathVariable Integer id, Model model) {
        model.addAttribute("activeDashPage", "announcement-detail");
        model.addAttribute("announcement", announcementService.getAnnouncementById(id).orElse(new Announcement()));

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/form")
    public String showAnnouncementForm(Model model) {
        model.addAttribute("activeDashPage", "announcement-form");
        model.addAttribute("announcement", new Announcement());

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/edit/{id}")
    public String showAnnouncementFormForUpdate(@PathVariable("id") int anId, Model model) {
        model.addAttribute("activeDashPage", "announcement-form");
        model.addAttribute("announcement", announcementService.getAnnouncementById(anId).orElse(new Announcement()));

        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/form/process")
    public String processAnnouncementForm(@ModelAttribute Announcement announcement) {
        announcementService.saveAnnouncement(announcement);

        return "redirect:/dashboard/announcements";
    }


    @GetMapping("/delete/{id}")
    public String deleteAnnouncement(@PathVariable Integer id) {
        announcementService.deleteAnnouncement(id);
        return "redirect:/dashboard/announcements";
    }
}
