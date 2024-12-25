package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.dtos.ChurchDetailDTO;
import com.churchwebsite.churchwebsite.entities.Address;
import com.churchwebsite.churchwebsite.entities.Baptism;
import com.churchwebsite.churchwebsite.enums.BaptismRequestType;
import com.churchwebsite.churchwebsite.enums.ServiceStatus;
import com.churchwebsite.churchwebsite.services.BaptismService;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("church-services/baptism")
public class BaptismController {

    private final String PUBLIC_CONTENT = "layouts/base";
    private final ChurchDetailDTO churchDetail;

    private final BaptismService baptismService;

    @Autowired
    public BaptismController(BaptismService baptismService,
                             ChurchDetailService churchDetailService) {
        this.baptismService = baptismService;
        this.churchDetail = churchDetailService.getChurchDetail();
    }

    @GetMapping("/form")
    public String showBaptismForm(Model model){

        model.addAttribute("baptismRequestType", BaptismRequestType.values());
        model.addAttribute("baptism", new Baptism());
        model.addAttribute("address", new Address());
        model.addAttribute("activeContentPage", "baptism-request-form");
        model.addAttribute("churchDetail", churchDetail);
        return PUBLIC_CONTENT;
    }

    @PostMapping("/form/process")
    public String processBaptismForm(@ModelAttribute Baptism baptism){
        baptism.setServiceStatus(ServiceStatus.REQUEST);
        Baptism savedBaptism = baptismService.save(baptism);

        return "redirect:/church-services?baptism";
    }
}
