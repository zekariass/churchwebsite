package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.Attachment;
import com.churchwebsite.churchwebsite.entities.MembershipCategory;
import com.churchwebsite.churchwebsite.services.MembershipCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("dashboard/memberships/categories")
public class MembershipCategoryController {
    private final MembershipCategoryService membershipCategoryService;

    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";

    @Autowired
    public MembershipCategoryController(MembershipCategoryService membershipCategoryService) {
        this.membershipCategoryService = membershipCategoryService;
    }

    @GetMapping("/form")
    public String showMembershipCategoryForm(Model model){

        model.addAttribute("activeDashPage", "memberships-category-form");
        model.addAttribute("membershipCategory", new MembershipCategory());
        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/processMembershipForm")
    public String processMembershipCategoryForm(@ModelAttribute MembershipCategory membershipCategory,
                                                Model model){

        MembershipCategory saveMembershipCategory = membershipCategoryService.save(membershipCategory);

        return "redirect:/dashboard/memberships/categories";
    }

    @GetMapping("")
    public String showMembershipCategoryList(Model model){

        List<MembershipCategory> categories = membershipCategoryService.findAll();

        model.addAttribute("activeDashPage", "memberships-category-list");
        model.addAttribute("membershipCategories", categories);
        return DASHBOARD_MAIN_PANEL;

    }

    @GetMapping("/detail/{id}")
    public String showMembershipCategoryDetail(Model model,
                                               @PathVariable("id") int memId){

        MembershipCategory membershipCategory = membershipCategoryService.findById(memId);

        model.addAttribute("activeDashPage", "memberships-category-detail");
        model.addAttribute("membershipCategory", membershipCategory);
        return DASHBOARD_MAIN_PANEL;

    }

    @GetMapping("/edit/{id}")
    public String showMembershipCategoryFormForEdit(Model model,
                                               @PathVariable("id") int memId){

        MembershipCategory membershipCategory = membershipCategoryService.findById(memId);

        model.addAttribute("activeDashPage", "memberships-category-form");
        model.addAttribute("membershipCategory", membershipCategory);
        return DASHBOARD_MAIN_PANEL;

    }

    @GetMapping("/delete/{id}")
    public String deleteMembershipCategory(Model model,
                                            @PathVariable("id") int memId){

        membershipCategoryService.deleteById(memId);
        return "redirect:/dashboard/memberships/categories";

    }
}
