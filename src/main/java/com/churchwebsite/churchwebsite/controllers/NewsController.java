package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.News;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.NewsService;
import com.churchwebsite.churchwebsite.services.PaginationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("dashboard/news")
public class NewsController {

    private final NewsService newsService;
    private final ChurchDetailService churchDetailService;

    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";
    private final PaginationService paginationService;

    @Autowired
    public NewsController(NewsService newsService, ChurchDetailService churchDetailService, PaginationService paginationService) {
        this.newsService = newsService;
        this.churchDetailService = churchDetailService;
        this.paginationService = paginationService;
    }

    @GetMapping("/form")
    public String showNewsForm(Model model){
        model.addAttribute("activeDashPage", "news-form");
        model.addAttribute("news", new News());
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "News Form");

        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/processForm")
    public String processNewsForm(@ModelAttribute News news,
                                  Model model){

        news.setArchived(false);
        news.setActive(true);
        News savedNews = newsService.save(news);

        return "redirect:/dashboard/news";
    }

    @GetMapping("")
    public String showNewsList(Model model,
                              @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                              @RequestParam(value = "size", required = false) Integer pageSize,
                               @RequestParam(value = "sortBy", defaultValue = "newsPostTime") String sortBy,
                              HttpServletRequest request){

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<News> pagedNews = newsService.findAll(page, pageSize, sortBy);

        List<News> newsList = pagedNews.getContent();

        model.addAttribute("activeDashPage", "news-list");
        model.addAttribute("newsList", newsList);

        model.addAttribute("currentPage", pagedNews.getNumber()+1);
        model.addAttribute("totalItems", pagedNews.getTotalElements());
        model.addAttribute("totalPages", pagedNews.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "News List");

        return DASHBOARD_MAIN_PANEL;
    }


    @GetMapping("/detail/{id}")
    public String showNewsDetail(@PathVariable(value = "id", required = true) int newsId,
                                 Model model){

        News news = newsService.findById(newsId);

        model.addAttribute("activeDashPage", "news-detail");
        model.addAttribute("news", news);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "News Detail");

        return DASHBOARD_MAIN_PANEL;
    }


    @GetMapping("/edit/{id}")
    public String showInfoEditForm(@PathVariable(value = "id", required = true) int infoId,
                                   Model model){

        News news = newsService.findById(infoId);

        model.addAttribute("activeDashPage", "news-form");
        model.addAttribute("news", news);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "News Form");

        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/processUpdateForm")
    public String processNewsUpdateForm(@ModelAttribute News news,
                                        Model model){
        News savedNews = newsService.save(news);
        return "redirect:/dashboard/news";
    }


    @GetMapping("/delete/{id}")
    public String deleteNews(@PathVariable("id") int newsId,
                             Model model){

        newsService.deleteById(newsId);
        return "redirect:/dashboard/news";
    }
}
