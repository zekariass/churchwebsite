package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.Settings;
import com.churchwebsite.churchwebsite.services.PaginationService;
import com.churchwebsite.churchwebsite.services.SettingsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("dashboard/settings")
public class SettingsController {
    private final SettingsService settingsService;
    private final PaginationService paginationService;

//    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";
    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-layouts/dash-base";

    @Autowired
    public SettingsController(SettingsService settingsService, PaginationService paginationService) {
        this.settingsService = settingsService;
        this.paginationService = paginationService;
    }

    @GetMapping("")
    public String getAllSettings(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false) Integer pageSize,
            HttpServletRequest request,
            Model model){

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<Settings> pagedSettings = settingsService.findAll(page, pageSize);

        List<Settings> settings = pagedSettings.getContent();

        model.addAttribute("activeDashPage", "settings-list");
        model.addAttribute("settings", settings);

        model.addAttribute("currentPage", pagedSettings.getNumber()+1);
        model.addAttribute("totalItems", pagedSettings.getTotalElements());
        model.addAttribute("totalPages", pagedSettings.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("pageTitle", "Settings List");

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/detail/{id}")
    public String showSettingDetail(@PathVariable("id") int id, Model model){
        model.addAttribute("activeDashPage", "setting-detail");
        model.addAttribute("setting", settingsService.findById(id));
        model.addAttribute("pageTitle", "Setting Detail");

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/delete/{id}")
    public String deleteSettingDetail(@PathVariable("id") int id, Model model){
        settingsService.deleteById(id);

        return "redirect:/dashboard/settings";
    }

    @GetMapping("/form")
    public String showSettingForm(Model model){
        model.addAttribute("activeDashPage", "setting-form");
        model.addAttribute("newSetting", new Settings());
        model.addAttribute("pageTitle", "Setting Form");

        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/form")
    public String showSettingUpdateForm(@Valid @ModelAttribute("newSetting") Settings settings,
                                        BindingResult result,
                                        Model model){

        if(result.hasErrors()){

            model.addAttribute("activeDashPage", "setting-form");
            model.addAttribute("newSetting", settings);
            model.addAttribute("pageTitle", "Setting Form");

            return DASHBOARD_MAIN_PANEL;
        }

        if(!settings.atLeastAValueIsProvided()){
            model.addAttribute("noValue", true);
            model.addAttribute("activeDashPage", "setting-form");
            model.addAttribute("newSetting", new Settings());
            model.addAttribute("pageTitle", "Setting Form");

            return DASHBOARD_MAIN_PANEL;
        }

        settingsService.save(settings);

        return "redirect:/dashboard/settings";
    }

    @GetMapping("/edit/{id}")
    public String showSettingUpdateForm(@PathVariable("id") int id, Model model){
        model.addAttribute("activeDashPage", "setting-form");
        model.addAttribute("newSetting", settingsService.findById(id));
        model.addAttribute("pageTitle", "Setting Form");

        return DASHBOARD_MAIN_PANEL;
    }

}
