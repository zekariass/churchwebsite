package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.ChurchServices;
import com.churchwebsite.churchwebsite.entities.News;
import com.churchwebsite.churchwebsite.services.ChurchServicesService;
import com.churchwebsite.churchwebsite.services.PaginationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("dashboard/services")
public class ChurchServicesController {
    private final ChurchServicesService churchServicesService;
    private final PaginationService paginationService;

    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";

    @Autowired
    public ChurchServicesController(ChurchServicesService churchServicesService,
                                    PaginationService paginationService) {
        this.churchServicesService = churchServicesService;
        this.paginationService = paginationService;
    }

    @GetMapping("/form")
    public String showServiceForm(Model model){

        model.addAttribute("service", new ChurchServices());
        model.addAttribute("activeDashPage", "service-form");

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/edit/{id}")
    public String showServiceEditForm(Model model,
                                      @PathVariable("id") int serviceId){

        ChurchServices churchService = churchServicesService.findById(serviceId);

        model.addAttribute("service", churchService);
        model.addAttribute("activeDashPage", "service-form");

        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/form/process")
    public String processServiceForm(Model model,
                                     @ModelAttribute ChurchServices service){

        ChurchServices savedService = churchServicesService.save(service);

        return "redirect:/dashboard/services";
    }


    @GetMapping("")
    public String showNewsList(Model model,
                               @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                               @RequestParam(value = "size", required = false) Integer pageSize,
                               @RequestParam(value = "sortBy", defaultValue = "serviceName") String sortBy,
                               HttpServletRequest request){

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<ChurchServices> pagedServices = churchServicesService.findAll(page, pageSize, sortBy);
        List<ChurchServices> churchServices = pagedServices.getContent();

        model.addAttribute("activeDashPage", "services-list");
        model.addAttribute("churchServices", churchServices);

        model.addAttribute("currentPage", pagedServices.getNumber()+1);
        model.addAttribute("totalItems", pagedServices.getTotalElements());
        model.addAttribute("totalPages", pagedServices.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("sortBy", sortBy);

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/detail/{id}")
    public String showServicesDetail(Model model,
                                     @PathVariable("id") int serviceId
                                   ){

        ChurchServices churchService = churchServicesService.findById(serviceId);

        model.addAttribute("churchService", churchService);
        model.addAttribute("activeDashPage", "service-detail");
        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/delete/{id}")
    public String deleteService(Model model,
                                @PathVariable("id") int serviceId){

        churchServicesService.deleteById(serviceId);

        return "redirect:/dashboard/services";
    }
}
