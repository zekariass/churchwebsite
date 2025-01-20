package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.Video;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.PaginationService;
import com.churchwebsite.churchwebsite.services.VideoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("videos")
public class VideoController {
    private final VideoService videoService;
//    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";
    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-layouts/dash-base";
    private final PaginationService paginationService;
    private final ChurchDetailService churchDetailService;

    @Autowired
    public VideoController(VideoService videoService,
                           PaginationService paginationService, ChurchDetailService churchDetailService) {
        this.videoService = videoService;
        this.paginationService = paginationService;
        this.churchDetailService = churchDetailService;
    }

    @GetMapping("/form")
    public String showVideoForm(Model model){

        model.addAttribute("activeDashPage", "video-form");
        model.addAttribute("video", new Video());
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Video Form");


        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/form/process")
    public String processVideoForm(@ModelAttribute Video video,  Model model){

        Video saveVideo = videoService.save(video);

        model.addAttribute("activeDashPage", "video-form");
        model.addAttribute("video", new Video());
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());

        return "redirect:/videos";
    }

    @GetMapping("")
    public String showVideoList(Model model,
                                @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                @RequestParam(value = "size", required = false) Integer pageSize,
                                HttpServletRequest request){

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());

        Page<Video> pagesVideoList = videoService.getVideoList(page, pageSize, Sort.by(Sort.Order.desc("uploadTime")));
        List<Video> videoList = pagesVideoList.getContent();

        model.addAttribute("activeDashPage", "videos-list");
        model.addAttribute("currentPage", pagesVideoList.getNumber() + 1);
        model.addAttribute("totalItems", pagesVideoList.getTotalElements());
        model.addAttribute("totalPages", pagesVideoList.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("videos", videoList);
        model.addAttribute("pageTitle", "Videos List");


        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/delete/{id}")
    public String deleteVideo(@PathVariable("id") int id){

        videoService.deleteVideoById(id);

        return "redirect:/videos";
    }


    @GetMapping("/edit/{id}")
    public String editVideo(@PathVariable("id") int id, Model model){

        Video video = videoService.findById(id);

        model.addAttribute("activeDashPage", "video-form");
        model.addAttribute("video", video);
        model.addAttribute("pageTitle", "Video Form");


        return DASHBOARD_MAIN_PANEL;
    }
}
