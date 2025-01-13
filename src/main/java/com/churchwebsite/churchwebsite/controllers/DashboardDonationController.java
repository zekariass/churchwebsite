package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.Donation;
import com.churchwebsite.churchwebsite.entities.DonationPurpose;
import com.churchwebsite.churchwebsite.services.PaginationService;
import com.churchwebsite.churchwebsite.services.payment.DonationPurposeService;
import com.churchwebsite.churchwebsite.services.payment.DonationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/dashboard/donations")
public class DashboardDonationController {

    private final DonationService donationService;
    private final PaginationService paginationService;
    private final DonationPurposeService donationPurposeService;

    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";

    @Autowired
    public DashboardDonationController(DonationService donationService, PaginationService paginationService, DonationPurposeService donationPurposeService) {
        this.donationService = donationService;
        this.paginationService = paginationService;
        this.donationPurposeService = donationPurposeService;
    }

    @GetMapping("")
    public String listDonations(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                @RequestParam(value = "size", required = false) Integer pageSize,
                                @RequestParam(value = "sortBy", defaultValue = "donationTime") String sortBy,
                                @RequestParam(value = "donationPurpose", required = false) DonationPurpose donationPurpose,
                                HttpServletRequest request,
                                Model model) {

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<Donation> pagedDonation;

        if(donationPurpose != null){
           pagedDonation = donationService.findByDonationPurpose(donationPurpose, page, pageSize, sortBy);
        }else{
            pagedDonation = donationService.findAll(page, pageSize, sortBy);
        }

        List<Donation> donations = pagedDonation.getContent();


        model.addAttribute("currentPage", pagedDonation.getNumber()+1);
        model.addAttribute("totalItems", pagedDonation.getTotalElements());
        model.addAttribute("totalPages", pagedDonation.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("pageTitle", "Donations List");

        model.addAttribute("donationPurposes", donationPurposeService.getAllDonationPurposes());

        model.addAttribute("donations", donations);
        model.addAttribute("activeDashPage", "donations-list");

        return DASHBOARD_MAIN_PANEL;
    }

}
