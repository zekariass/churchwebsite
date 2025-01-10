package com.churchwebsite.churchwebsite.controller_advices;

import com.churchwebsite.churchwebsite.services.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class SettingAdvice {

    private final SettingsService settingsService;

    @Autowired
    public SettingAdvice(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @ModelAttribute
    public void settings(Model model){
        model.addAttribute("settings", settingsService.findAll());
    }

    @ModelAttribute
    public void tinyMceKey(Model model){
        model.addAttribute("tinyMceKey", settingsService.findBySettingName("TINY_MCE_KEY"));
    }
}
