package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.dtos.ChurchDetailDTO;
import com.churchwebsite.churchwebsite.entities.Address;
import com.churchwebsite.churchwebsite.entities.HolyMatrimony;
import com.churchwebsite.churchwebsite.enums.HolyMatrimonyRequestType;
import com.churchwebsite.churchwebsite.enums.ServiceStatus;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.HolyMatrimonyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("church-services/matrimony")
public class HolyMatrimonyController {

    private final String PUBLIC_CONTENT = "layouts/base";
    private final ChurchDetailDTO churchDetail;

    private final HolyMatrimonyService holyMatrimonyService;

    @Autowired
    public HolyMatrimonyController(HolyMatrimonyService holyMatrimonyService,
                             ChurchDetailService churchDetailService) {
        this.holyMatrimonyService = holyMatrimonyService;
        this.churchDetail = churchDetailService.getChurchDetail();
    }

    @GetMapping("/form")
    public String showMatrimonyForm(Model model){

        model.addAttribute("matrimonyRequestType", HolyMatrimonyRequestType.values());
        model.addAttribute("matrimony", new HolyMatrimony());
        model.addAttribute("address", new Address());
        model.addAttribute("activeContentPage", "matrimony-request-form");
        model.addAttribute("churchDetail", churchDetail);
        return PUBLIC_CONTENT;
    }

    @PostMapping("/form/process")
    public String processMatrimonyForm(@ModelAttribute HolyMatrimony matrimony){
        matrimony.setService_status(ServiceStatus.REQUEST);
        HolyMatrimony savedMatrimony = holyMatrimonyService.save(matrimony);

        return "redirect:/church-services?matrimony";
    }
}
