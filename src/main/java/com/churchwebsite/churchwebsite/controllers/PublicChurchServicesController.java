package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.ChurchServices;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.ChurchServicesService;
import com.churchwebsite.churchwebsite.services.PaginationService;
import jakarta.servlet.http.HttpServletRequest;
import org.jsoup.Jsoup;
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
@RequestMapping("church-services")
public class PublicChurchServicesController {
    private final ChurchServicesService churchServicesService;
    private final PaginationService paginationService;
    private final ChurchDetailService churchDetailService;

    private final String PUBLIC_CONTENT = "layouts/base";

    @Autowired
    public PublicChurchServicesController(ChurchServicesService churchServicesService,
                                          ChurchDetailService churchDetailService,
                                          PaginationService paginationService) {
        this.churchServicesService = churchServicesService;
        this.paginationService = paginationService;
        this.churchDetailService = churchDetailService;
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

        churchServices.forEach(service -> {
            String excerpt = generateExcerpt(service.getServiceDescription(), 200);
            service.setExcerpt(excerpt);
        });

        model.addAttribute("activeContentPage", "services-list");
        model.addAttribute("churchServices", churchServices);

        model.addAttribute("currentPage", pagedServices.getNumber()+1);
        model.addAttribute("totalItems", pagedServices.getTotalElements());
        model.addAttribute("totalPages", pagedServices.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Services List");

        return PUBLIC_CONTENT;
    }

    private String generateExcerpt(String richText, int length) {
        if(richText == null || richText.isEmpty()){
            return "";
        }else{
            String plainText = Jsoup.parse(richText).text();
            return plainText.length() > length ? plainText.substring(0, length) : plainText;
        }
    }

    @GetMapping("/detail/{id}")
    public String showServicesDetail(Model model,
                                     @PathVariable("id") int serviceId){

        ChurchServices churchService = churchServicesService.findById(serviceId);

        model.addAttribute("churchService", churchService);
        model.addAttribute("activeContentPage", "service-detail");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Service Detail");

        return PUBLIC_CONTENT;
    }
}
