package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.Member;
import com.churchwebsite.churchwebsite.entities.MemberDependent;
import com.churchwebsite.churchwebsite.entities.MembershipAmount;
import com.churchwebsite.churchwebsite.enums.Gender;
import com.churchwebsite.churchwebsite.enums.MembershipPaymentMethod;
import com.churchwebsite.churchwebsite.enums.Relationship;
import com.churchwebsite.churchwebsite.services.*;
import com.churchwebsite.churchwebsite.utils.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("members")
public class PublicMemberController {

    private final MemberService memberService;
    private final MembershipAmountService membershipAmountService;
    private final SettingsService settingsService;
    private final UserService userService;
    private final ChurchDetailService churchDetailService;

    private final String PUBLIC_CONTENT = "layouts/base";

    @Autowired
    public PublicMemberController(MemberService memberService,
                                  MembershipAmountService membershipAmountService,
                                  SettingsService settingsService,
                                  UserService userService,
                                  ChurchDetailService churchDetailService) {
        this.memberService = memberService;
        this.membershipAmountService = membershipAmountService;
        this.settingsService = settingsService;
        this.userService = userService;
        this.churchDetailService = churchDetailService;
    }

    @GetMapping("/form")
    public String showMemberForm(Model model){

        List<MembershipAmount> membershipAmounts = membershipAmountService.findAll();

        String localLanguageCode = settingsService.findBySettingName("LOCALE_LANGUAGE_CODE").getSettingValueChar();
        String localCountryCode = settingsService.findBySettingName("LOCALE_COUNTRY_CODE").getSettingValueChar();

        localLanguageCode = localLanguageCode != null ? localLanguageCode: "GB";
        localCountryCode = localCountryCode != null ? localCountryCode: "en";

        Locale locale = new Locale(localLanguageCode, localCountryCode);

        Currency currency = Currency.getInstance(locale);

        model.addAttribute("activeContentPage", "member-form");
        model.addAttribute("member", new Member());
        model.addAttribute("genders", Gender.values());
        model.addAttribute("relationshipTypes", Relationship.values());
        model.addAttribute("currencyCode", currency.getCurrencyCode());
        model.addAttribute("membershipAmounts", membershipAmounts);
        model.addAttribute("paymentMethods", MembershipPaymentMethod.values());
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());

        return PUBLIC_CONTENT;
    }


    @PostMapping("/form/process")
    public String processMemberForm(@ModelAttribute Member member,
                                    @RequestParam("byAdmin") String byAdmin){

        CustomUserDetails user = userService.getCurrentUser();

        if(!byAdmin.equals("true") && user != null){
            member.setUser(user.getUser());
        }

        List<MemberDependent> dependents = member.getNonNullDependents();
        member.setMemberDependents(dependents);

        for(MemberDependent dependent: member.getMemberDependents()){
            if (!dependent.getFirstName().isEmpty() && !dependent.getLastName().isEmpty()){
                dependent.setMember(member);
            }
        }

        Member savedMember = memberService.save(member);

        return "redirect:/members/form/submitted?saved";
    }

    @GetMapping("/form/submitted")
    public String memberFormSubmitted(Model model){

        model.addAttribute("activeContentPage", "member-form-submitted");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());

        return PUBLIC_CONTENT;
    }
}
