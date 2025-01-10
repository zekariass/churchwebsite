package com.churchwebsite.churchwebsite.controller_advices;

import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ChurchDetailAdvice {

    private final ChurchDetailService churchDetailService;

    public ChurchDetailAdvice(ChurchDetailService churchDetailService) {
        this.churchDetailService = churchDetailService;
    }

    @ModelAttribute
    public void churchDetail(Model model){
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
    }
}
