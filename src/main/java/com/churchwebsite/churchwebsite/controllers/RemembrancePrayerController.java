package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.dtos.ChurchDetailDTO;
import com.churchwebsite.churchwebsite.entities.Address;
import com.churchwebsite.churchwebsite.entities.RemembrancePrayer;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.RemembrancePrayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("church-services/remembrance")
public class RemembrancePrayerController {

    private final String PUBLIC_CONTENT = "layouts/base";
    private final ChurchDetailDTO churchDetail;

    private final RemembrancePrayerService remembrancePrayerService;

    @Autowired
    public RemembrancePrayerController(RemembrancePrayerService remembrancePrayerService,
                                   ChurchDetailService churchDetailService) {
        this.remembrancePrayerService = remembrancePrayerService;
        this.churchDetail = churchDetailService.getChurchDetail();
    }

    @GetMapping("/form")
    public String showRemembranceForm(Model model){

        model.addAttribute("remembrance", new RemembrancePrayer());
        model.addAttribute("address", new Address());
        model.addAttribute("activeContentPage", "remembrance-request-form");
        model.addAttribute("churchDetail", churchDetail);
        return PUBLIC_CONTENT;
    }

    @PostMapping("/form/process")
    public String processMatrimonyForm(@ModelAttribute RemembrancePrayer remembrancePrayer){
        RemembrancePrayer savedMatrimony = remembrancePrayerService.save(remembrancePrayer);

        return "redirect:/church-services?remembrance";
    }
}
