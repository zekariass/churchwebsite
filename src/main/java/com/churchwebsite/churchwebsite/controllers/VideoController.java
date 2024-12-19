package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.Album;
import com.churchwebsite.churchwebsite.entities.Video;
import com.churchwebsite.churchwebsite.services.PaginationService;
import com.churchwebsite.churchwebsite.services.VideoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("videos")
public class VideoController {
    private final VideoService videoService;
    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";
    private final PaginationService paginationService;

    @Autowired
    public VideoController(VideoService videoService,
                           PaginationService paginationService) {
        this.videoService = videoService;
        this.paginationService = paginationService;
    }

    @GetMapping("/form")
    public String showVideoForm(Model model){

        model.addAttribute("activeDashPage", "video-form");
        model.addAttribute("video", new Video());

        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/form/process")
    public String processVideoForm(@ModelAttribute Video video,  Model model){

        Video saveVideo = videoService.save(video);

        model.addAttribute("activeDashPage", "video-form");
        model.addAttribute("video", new Video());

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("")
    public String showVideoList(Model model,
                                @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                @RequestParam(value = "size", required = false) Integer pageSize,
                                HttpServletRequest request){

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<Video> pagesVideoList = videoService.getVideoList(page, pageSize, Sort.by(Sort.Order.desc("uploadTime")));
        List<Video> videoList = pagesVideoList.getContent();

        model.addAttribute("activeDashPage", "videos-list");
        model.addAttribute("currentPage", pagesVideoList.getNumber() + 1);
        model.addAttribute("totalItems", pagesVideoList.getTotalElements());
        model.addAttribute("totalPages", pagesVideoList.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("videos", videoList);

        return DASHBOARD_MAIN_PANEL;
    }
}
