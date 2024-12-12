package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.dtos.ChurchDetailDTO;
import com.churchwebsite.churchwebsite.entities.News;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.NewsService;
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
@RequestMapping("/news")
public class PublicNewsController {
    private final NewsService newsService;
    private final PaginationService paginationService;
    private final ChurchDetailDTO churchDetail;


    private String PUBLIC_CONTENT = "layouts/base";

    @Autowired
    public PublicNewsController(NewsService newsService,
                                ChurchDetailService churchDetailService,
                                PaginationService paginationService) {
        this.newsService = newsService;
        this.paginationService = paginationService;

        churchDetail = churchDetailService.getChurchDetail();

    }

    @GetMapping("")
    public String showNewsPublic(Model model,
                                 @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                 @RequestParam(value = "size", required = false) Integer pageSize,
                                 @RequestParam(value = "sortBy", defaultValue = "newsPostTime") String sortBy,
                                 HttpServletRequest request){


        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<News> pagedNews = newsService.findAll(page, pageSize, sortBy);
        List<News> newsList = pagedNews.getContent();

        model.addAttribute("newsList", newsList);
        model.addAttribute("activeContentPage", "news-list");
        model.addAttribute("churchDetail", churchDetail);

        model.addAttribute("currentPage", pagedNews.getNumber()+1);
        model.addAttribute("totalItems", pagedNews.getTotalElements());
        model.addAttribute("totalPages", pagedNews.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("sortBy", sortBy);


        return PUBLIC_CONTENT;
    }


    @GetMapping("/detail/{id}")
    public String showNewsDetail(@PathVariable(value = "id", required = true) int newsId,
                                 Model model){

        News news = newsService.findById(newsId);

        model.addAttribute("activeContentPage", "news-detail");
        model.addAttribute("news", news);
        model.addAttribute("churchDetail", churchDetail);

        return PUBLIC_CONTENT;
    }
}
