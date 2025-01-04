package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.MembershipAmount;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.MembershipAmountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("dashboard/members/membership-amounts")
public class MembershipAmountController {
    private final MembershipAmountService membershipAmountService;
    private final ChurchDetailService churchDetailService;

    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";

    @Autowired
    public MembershipAmountController(MembershipAmountService membershipAmountService, ChurchDetailService churchDetailService) {
        this.membershipAmountService = membershipAmountService;
        this.churchDetailService = churchDetailService;
    }

    @GetMapping("/form")
    public String showMembershipCategoryForm(Model model){

        model.addAttribute("activeDashPage", "membership-amount-form");
        model.addAttribute("membershipAmount", new MembershipAmount());
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());

        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/form/process")
    public String processMembershipCategoryForm(@ModelAttribute MembershipAmount membershipAmount,
                                                Model model){

        MembershipAmount savedMembershipAmount = membershipAmountService.save(membershipAmount);

        return "redirect:/dashboard/members/membership-amounts";
    }

    @GetMapping("")
    public String showMembershipAmountList(Model model){

        List<MembershipAmount> amounts = membershipAmountService.findAll();

        model.addAttribute("activeDashPage", "membership-amounts-list");
        model.addAttribute("membershipAmounts", amounts);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());

        return DASHBOARD_MAIN_PANEL;

    }


    @GetMapping("/edit/{id}")
    public String showMembershipCategoryFormForEdit(Model model,
                                               @PathVariable("id") int memId){

        MembershipAmount membershipAmount = membershipAmountService.findById(memId);

        model.addAttribute("activeDashPage", "membership-amount-form");
        model.addAttribute("membershipAmount", membershipAmount);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());

        return DASHBOARD_MAIN_PANEL;

    }

    @GetMapping("/delete/{id}")
    public String deleteMembershipAmount(Model model,
                                         @PathVariable("id") int memId){

        membershipAmountService.deleteById(memId);
        return "redirect:/dashboard/members/membership-amounts";

    }
}
