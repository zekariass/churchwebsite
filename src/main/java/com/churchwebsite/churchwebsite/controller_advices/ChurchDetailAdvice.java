package com.churchwebsite.churchwebsite.controller_advices;

import com.churchwebsite.churchwebsite.dtos.ChurchDetailDTO;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;

@ControllerAdvice
public class ChurchDetailAdvice {

    private final ChurchDetailService churchDetailService;

    public ChurchDetailAdvice(ChurchDetailService churchDetailService) {
        this.churchDetailService = churchDetailService;
    }

    @ModelAttribute
    public void churchDetail(Model model) throws IOException {
        ChurchDetailDTO churchDetailDTO = churchDetailService.getChurchDetail();
        model.addAttribute("churchDetail", churchDetailDTO);
    }
}
