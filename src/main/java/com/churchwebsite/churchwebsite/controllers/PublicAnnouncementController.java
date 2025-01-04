package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.Announcement;
import com.churchwebsite.churchwebsite.services.AnnouncementService;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.PaginationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/announcements")
public class PublicAnnouncementController {
    private final AnnouncementService announcementService;
    private final ChurchDetailService churchDetailService;
    private final PaginationService paginationService;

    private final String PUBLIC_CONTENT = "layouts/base";

    @Autowired
    public PublicAnnouncementController(AnnouncementService announcementService, ChurchDetailService churchDetailService, PaginationService paginationService) {
        this.announcementService = announcementService;
        this.churchDetailService = churchDetailService;
        this.paginationService = paginationService;
    }

    @GetMapping("")
    public String getAllAnnouncements(Model model,
                                      @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                      @RequestParam(value = "size", required = false) Integer pageSize,
                                      @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                                      HttpServletRequest request){

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<Announcement> pagedAnnouncement = announcementService.getAllShipments(page, pageSize, sortBy);
        List<Announcement> announcements = pagedAnnouncement.getContent();

        model.addAttribute("activeContentPage", "announcements-list");
        model.addAttribute("announcements", announcements);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());

        model.addAttribute("currentPage", pagedAnnouncement.getNumber()+1);
        model.addAttribute("totalItems", pagedAnnouncement.getTotalElements());
        model.addAttribute("totalPages", pagedAnnouncement.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("sortBy", sortBy);

        return PUBLIC_CONTENT;
    }

    @GetMapping("/detail/{id}")
    public String getAnnouncementById(@PathVariable Integer id, Model model) {
        model.addAttribute("activeContentPage", "announcement-detail");
        model.addAttribute("announcement", announcementService.getAnnouncementById(id).orElse(new Announcement()));
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());

        return PUBLIC_CONTENT;
    }

}
