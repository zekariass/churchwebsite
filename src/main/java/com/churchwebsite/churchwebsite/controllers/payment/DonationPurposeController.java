package com.churchwebsite.churchwebsite.controllers.payment;

import com.churchwebsite.churchwebsite.entities.DonationPurpose;
import com.churchwebsite.churchwebsite.services.payment.DonationPurposeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard/donations/purposes")
public class DonationPurposeController {

    private final DonationPurposeService donationPurposeService;
//    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";
    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-layouts/dash-base";

    @Autowired
    public DonationPurposeController(DonationPurposeService donationPurposeService) {
        this.donationPurposeService = donationPurposeService;
    }

    @GetMapping
    public String getAllDonationPurposes(Model model) {
        List<DonationPurpose> purposes = donationPurposeService.getAllDonationPurposes();
        model.addAttribute("purposes", purposes);
        model.addAttribute("activeDashPage", "donation-purposes-list");

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/form")
    public String showNewDonationPurposeForm(Model model) {
        model.addAttribute("donationPurpose", new DonationPurpose());
        model.addAttribute("activeDashPage", "donation-purpose-form");

        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/form")
    public String saveDonationPurpose(@Valid @ModelAttribute DonationPurpose donationPurpose,
                                      BindingResult result,
                                      Model model) {

        if(result.hasErrors()){
            model.addAttribute("donationPurpose", donationPurpose);
            model.addAttribute("activeDashPage", "donation-purpose-form");

            return DASHBOARD_MAIN_PANEL;
        }

        donationPurposeService.saveDonationPurpose(donationPurpose);

        return "redirect:/dashboard/donations/purposes";
    }

    @GetMapping("/detail/{id}")
    public String getDonationPurposeById(@PathVariable Integer id, Model model) {
        Optional<DonationPurpose> donationPurpose = donationPurposeService.getDonationPurposeById(id);
        donationPurpose.ifPresent(p -> model.addAttribute("donationPurpose", p));
        model.addAttribute("activeDashPage", "donation-purpose-detail");

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/edit/{id}")
    public String showEditDonationPurposeForm(@PathVariable Integer id, Model model) {
        Optional<DonationPurpose> donationPurpose = donationPurposeService.getDonationPurposeById(id);
        donationPurpose.ifPresent(p -> model.addAttribute("donationPurpose", p));
        model.addAttribute("activeDashPage", "donation-purpose-form");

        return DASHBOARD_MAIN_PANEL;
    }


    @GetMapping("/delete/{id}")
    public String deleteDonationPurpose(@PathVariable Integer id) {
        donationPurposeService.deleteDonationPurpose(id);
        return "redirect:/dashboard/donations/purposes";
    }
}
