package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.Baptism;
import com.churchwebsite.churchwebsite.entities.ChurchServices;
import com.churchwebsite.churchwebsite.entities.HolyMatrimony;
import com.churchwebsite.churchwebsite.entities.RemembrancePrayer;
import com.churchwebsite.churchwebsite.enums.ServiceStatus;
import com.churchwebsite.churchwebsite.services.*;
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
    private final BaptismService baptismService;
    private final HolyMatrimonyService holyMatrimonyService;
    private final RemembrancePrayerService remembrancePrayerService;

    private final ChurchDetailService churchDetailService;

    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";


    @Autowired
    public ChurchServicesController(ChurchServicesService churchServicesService,
                                    BaptismService baptismService,
                                    HolyMatrimonyService holyMatrimonyService,
                                    RemembrancePrayerService remembrancePrayerService,
                                    PaginationService paginationService, ChurchDetailService churchDetailService) {
        this.churchServicesService = churchServicesService;
        this.paginationService = paginationService;
        this.baptismService = baptismService;
        this.holyMatrimonyService = holyMatrimonyService;
        this.remembrancePrayerService = remembrancePrayerService;
        this.churchDetailService = churchDetailService;
    }

    @GetMapping("/form")
    public String showServiceForm(Model model){

        model.addAttribute("service", new ChurchServices());
        model.addAttribute("activeDashPage", "service-form");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Service Form");


        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/edit/{id}")
    public String showServiceEditForm(Model model,
                                      @PathVariable("id") int serviceId){

        ChurchServices churchService = churchServicesService.findById(serviceId);

        model.addAttribute("service", churchService);
        model.addAttribute("activeDashPage", "service-form");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Service Form");

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
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Services List");

        return DASHBOARD_MAIN_PANEL;
    }


    @GetMapping("/detail/{id}")
    public String showServicesDetail(Model model,
                                     @PathVariable("id") int serviceId
                                   ){

        ChurchServices churchService = churchServicesService.findById(serviceId);

        model.addAttribute("churchService", churchService);
        model.addAttribute("activeDashPage", "service-detail");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Service Detail");

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/delete/{id}")
    public String deleteService(Model model,
                                @PathVariable("id") int serviceId){

        churchServicesService.deleteById(serviceId);

        return "redirect:/dashboard/services";
    }

//    ============ BAPTISM ==============================

    @GetMapping("/baptism/requests")
    public String showBaptismRequests(Model model,
                               @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                               @RequestParam(value = "size", required = false) Integer pageSize,
                               @RequestParam(value = "sortBy", defaultValue = "requestDate") String sortBy,
                               HttpServletRequest request){

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<Baptism> pagedBaptismRequests = baptismService.findAll(page, pageSize, sortBy);
        List<Baptism> baptismRequests = pagedBaptismRequests.getContent();

        model.addAttribute("activeDashPage", "baptism-requests-list");
        model.addAttribute("baptismRequests", baptismRequests);

        model.addAttribute("currentPage", pagedBaptismRequests.getNumber()+1);
        model.addAttribute("totalItems", pagedBaptismRequests.getTotalElements());
        model.addAttribute("totalPages", pagedBaptismRequests.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("sortBy", sortBy);

        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Baptism Requests List");

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/baptism/requests/detail/{id}")
    public String showBaptismDetail(Model model,
                                     @PathVariable("id") int requestId){

        Baptism baptism = baptismService.findById(requestId);

        model.addAttribute("baptism", baptism);
        model.addAttribute("activeDashPage", "baptism-request-detail");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Baptism Request Detail");

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/baptism/update-status/form/{id}")
    public String showChangeStatusForm(Model model,
                                       @PathVariable("id") int requestId){
        Baptism baptism = baptismService.findById(requestId);

        model.addAttribute("baptism", baptism);
        model.addAttribute("baptismRequestStatus", ServiceStatus.values());
        model.addAttribute("activeDashPage", "baptism-request-status-update");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Baptism Request Form");

        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/baptism/update-status/form/process")
    public String processChangeStatusForm(Model model,
                                       @ModelAttribute Baptism baptism,
                                       @RequestParam("serviceStatus") ServiceStatus serviceStatus){
        Baptism baptism2 = baptismService.findById(baptism.getRequestId());
        baptism2.setServiceStatus(serviceStatus);
        baptismService.save(baptism2);

        return "redirect:/dashboard/services/baptism/requests/detail/"+baptism.getRequestId();
    }

//    =============== MATRIMONY =============================


    @GetMapping("/matrimony/requests")
    public String showMatrimonyRequests(Model model,
                                      @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                      @RequestParam(value = "size", required = false) Integer pageSize,
                                      @RequestParam(value = "sortBy", defaultValue = "requestDate") String sortBy,
                                      HttpServletRequest request){

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<HolyMatrimony> pagedMatrimonyRequests = holyMatrimonyService.findAll(page, pageSize, sortBy);
        List<HolyMatrimony> matrimonyRequests = pagedMatrimonyRequests.getContent();

        model.addAttribute("activeDashPage", "matrimony-requests-list");
        model.addAttribute("matrimonyRequests", matrimonyRequests);

        model.addAttribute("currentPage", pagedMatrimonyRequests.getNumber()+1);
        model.addAttribute("totalItems", pagedMatrimonyRequests.getTotalElements());
        model.addAttribute("totalPages", pagedMatrimonyRequests.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("sortBy", sortBy);

        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Matrimony Requests List");

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/matrimony/requests/detail/{id}")
    public String showMatrimonyDetail(Model model,
                                    @PathVariable("id") int requestId){

        HolyMatrimony matrimony = holyMatrimonyService.findById(requestId);

        model.addAttribute("matrimony", matrimony);
        model.addAttribute("activeDashPage", "matrimony-request-detail");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Matrimony Request Detail");

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/matrimony/update-status/form/{id}")
    public String showMatrimonyChangeServiceStatusForm(Model model,
                                       @PathVariable("id") int requestId){
        HolyMatrimony matrimony = holyMatrimonyService.findById(requestId);

        model.addAttribute("matrimony", matrimony);
        model.addAttribute("matrimonyRequestStatus", ServiceStatus.values());
        model.addAttribute("activeDashPage", "matrimony-request-status-update");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Matrimony Request Form");

        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/matrimony/update-status/form/process")
    public String processMatrimonyChangeServiceStatusForm(Model model,
                                          @ModelAttribute HolyMatrimony holyMatrimony,
                                          @RequestParam("service_status") ServiceStatus serviceStatus){
        HolyMatrimony matrimony = holyMatrimonyService.findById(holyMatrimony.getRequestId());
        matrimony.setService_status(serviceStatus);
        holyMatrimonyService.save(matrimony);

        return "redirect:/dashboard/services/matrimony/requests/detail/"+matrimony.getRequestId();
    }


    //    =============== REMEMBRANCE =============================

    @GetMapping("/remembrance/requests")
    public String showRemembranceRequests(Model model,
                                        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                        @RequestParam(value = "size", required = false) Integer pageSize,
                                        @RequestParam(value = "sortBy", defaultValue = "requestDate") String sortBy,
                                        HttpServletRequest request){

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<RemembrancePrayer> pagedRemembrancePrayerRequests = remembrancePrayerService.findAll(page, pageSize, sortBy);
        List<RemembrancePrayer> remembrancePrayerRequests = pagedRemembrancePrayerRequests.getContent();

        model.addAttribute("activeDashPage", "remembrance-requests-list");
        model.addAttribute("remembranceRequests", remembrancePrayerRequests);

        model.addAttribute("currentPage", pagedRemembrancePrayerRequests.getNumber()+1);
        model.addAttribute("totalItems", pagedRemembrancePrayerRequests.getTotalElements());
        model.addAttribute("totalPages", pagedRemembrancePrayerRequests.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("sortBy", sortBy);

        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Remembrance Request List");


        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/remembrance/requests/detail/{id}")
    public String showRemembranceDetail(Model model,
                                      @PathVariable("id") int requestId){

        RemembrancePrayer remembrancePrayer = remembrancePrayerService.findById(requestId);

        model.addAttribute("remembrance", remembrancePrayer);
        model.addAttribute("activeDashPage", "remembrance-request-detail");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Remembrance Request Detail");

        return DASHBOARD_MAIN_PANEL;
    }
}
