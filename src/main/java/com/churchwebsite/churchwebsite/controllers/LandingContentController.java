package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.LandingContent;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.LandingContentService;
import com.churchwebsite.churchwebsite.services.PaginationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("dashboard/landing-contents")
public class LandingContentController {

    private final LandingContentService landingContentService;
    private final PaginationService paginationService;
    private final ChurchDetailService churchDetailService;

    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";

    @Autowired
    public LandingContentController(LandingContentService landingContentService,
                                    PaginationService paginationService, ChurchDetailService churchDetailService) {
        this.landingContentService = landingContentService;
        this.paginationService = paginationService;
        this.churchDetailService = churchDetailService;
    }

    @GetMapping("/form")
    public String showInfoForm(Model model){

        model.addAttribute("activeDashPage", "landing-content-form");
        model.addAttribute("landingContent", new LandingContent());
        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("")
    public String showInfoList(Model model,
                               @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                               @RequestParam(value = "size", required = false) Integer pageSize,
                               @RequestParam(value = "sortBy", defaultValue = "", required = false) String sortBy,
                               HttpServletRequest request){

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<LandingContent> pagedContent = landingContentService.findAll(page, pageSize, sortBy);
        List<LandingContent> landingContentList = pagedContent.getContent();

        model.addAttribute("activeDashPage", "landing-content-list");
        model.addAttribute("landingContentList", landingContentList);

        model.addAttribute("currentPage", pagedContent.getNumber()+1);
        model.addAttribute("totalItems", pagedContent.getTotalElements());
        model.addAttribute("totalPages", pagedContent.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());


        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/processForm")
    public String processInfoForm(@ModelAttribute LandingContent landingContent,
                                  Model model){

        landingContent.setArchived(false);
        landingContent.setActive(true);
        LandingContent savedInfo = landingContentService.save(landingContent);

        return "redirect:/dashboard/landing-contents";
    }

    @GetMapping("/detail/{id}")
    public String showInfoDetail(@PathVariable(value = "id", required = true) int infoId,
                                 Model model){

        LandingContent landingContent = landingContentService.findById(infoId);

        model.addAttribute("activeDashPage", "landing-content-detail");
        model.addAttribute("landingContent", landingContent);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());

        return DASHBOARD_MAIN_PANEL;
    }


    @GetMapping("/edit/{id}")
    public String showInfoEditForm(@PathVariable(value = "id", required = true) int infoId,
                                 Model model){

        LandingContent landingContent = landingContentService.findById(infoId);

        model.addAttribute("activeDashPage", "landing-content-form");
        model.addAttribute("landingContent", landingContent);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());

        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/processUpdateForm")
    public String processInfoUpdateForm(@ModelAttribute LandingContent landingContent,
                                  Model model){
        LandingContent savedInfo = landingContentService.save(landingContent);

        return "redirect:/dashboard/landing-contents";
    }





    @GetMapping("/delete/{id}")
    public String deleteInfoUpdateForm(@PathVariable("id") int contentId,
                                        Model model){

        landingContentService.deleteById(contentId);

        return "redirect:/dashboard/landing-contents";
    }
}
