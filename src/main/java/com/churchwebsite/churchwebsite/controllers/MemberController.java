package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.*;
import com.churchwebsite.churchwebsite.enums.Gender;
import com.churchwebsite.churchwebsite.enums.MembershipPaymentMethod;
import com.churchwebsite.churchwebsite.enums.Relationship;
import com.churchwebsite.churchwebsite.services.*;
import com.churchwebsite.churchwebsite.utils.CustomUserDetails;
import com.churchwebsite.churchwebsite.utils.LocaleUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("dashboard/members")
public class MemberController {

    private final MemberService memberService;
    private final MembershipAmountService membershipAmountService;
    private final PaginationService paginationService;
    private final SettingsService settingsService;
    private final UserService userService;
    private final LocaleUtil localeUtil;
    private final ChurchDetailService churchDetailService;

//    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";
    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-layouts/dash-base";

    @Autowired
    public MemberController(MemberService memberService,
                            MembershipAmountService membershipAmountService,
                            PaginationService paginationService,
                            SettingsService settingsService,
                            UserService userService,
                            LocaleUtil localeUtil, ChurchDetailService churchDetailService) {
        this.memberService = memberService;
        this.membershipAmountService = membershipAmountService;
        this.paginationService = paginationService;
        this.settingsService = settingsService;
        this.userService = userService;
        this.localeUtil = localeUtil;
        this.churchDetailService = churchDetailService;
    }

    @GetMapping("/form")
    public String showMemberForm(Model model){

        setModelAttributesToShowForm(model);
        model.addAttribute("member", new Member());

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("")
    public String showNewsList(Model model,
                               @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                               @RequestParam(value = "size", required = false) Integer pageSize,
                               @RequestParam(value = "sortBy", defaultValue = "membershipDate") String sortBy,
                               HttpServletRequest request){

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<Member> pagedMembers = memberService.findAll(page, pageSize, sortBy);
        List<Member> members = pagedMembers.getContent();

        model.addAttribute("activeDashPage", "members-list");
        model.addAttribute("members", members);

        model.addAttribute("currentPage", pagedMembers.getNumber()+1);
        model.addAttribute("totalItems", pagedMembers.getTotalElements());
        model.addAttribute("totalPages", pagedMembers.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Memberships List");

        return DASHBOARD_MAIN_PANEL;
    }


    @PostMapping("/form")
    public String processMemberForm(@Valid @ModelAttribute Member member,
                                    BindingResult result,
                                    @RequestParam("byAdmin") String byAdmin,
                                    Model model){

        if(result.hasErrors()){
            setModelAttributesToShowForm(model);
            model.addAttribute("member", member);

            return DASHBOARD_MAIN_PANEL;
        }

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

        return "redirect:/dashboard/members";
    }

    private void setModelAttributesToShowForm(Model model) {
        List<MembershipAmount> membershipAmounts = membershipAmountService.findAll();

        String localLanguageCode = settingsService.findBySettingName("LOCALE_LANGUAGE_CODE").getSettingValueChar();
        String localCountryCode = settingsService.findBySettingName("LOCALE_COUNTRY_CODE").getSettingValueChar();

        localLanguageCode = localLanguageCode != null ? localLanguageCode: "GB";
        localCountryCode = localCountryCode != null ? localCountryCode: "en";

        Locale locale = new Locale(localLanguageCode, localCountryCode);

        Currency currency = Currency.getInstance(locale);

        model.addAttribute("activeDashPage", "member-form");
        model.addAttribute("genders", Gender.values());
        model.addAttribute("relationshipTypes", Relationship.values());
        model.addAttribute("paymentMethods", MembershipPaymentMethod.values());
        model.addAttribute("currencyCode", currency.getCurrencyCode());
        model.addAttribute("membershipAmounts", membershipAmounts);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Membership Form");
    }


    @GetMapping("/edit/{id}")
    public String editMemberDetail(@PathVariable("id") int memberId,
                                    Model model){

        Member member = memberService.findById(memberId);
        member.setMemberDependents(null);

        List<MembershipAmount> membershipAmounts = membershipAmountService.findAll();

        model.addAttribute("activeDashPage", "member-form");
        model.addAttribute("member", member);
        model.addAttribute("genders", Gender.values());
        model.addAttribute("currencyCode", localeUtil.getCurrency().getCurrencyCode());
        model.addAttribute("membershipAmounts", membershipAmounts);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Membership Form");

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/detail/{id}")
    public String showMemberDetail(@PathVariable("id") int memberId,
                                   Model model){

        Member member = memberService.findById(memberId);

        model.addAttribute("activeDashPage", "member-detail");
        model.addAttribute("member", member);
        model.addAttribute("genders", Gender.values());
        model.addAttribute("currencyCode", localeUtil.getCurrency().getCurrencyCode());
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Membership Detail");

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") int memberId){

        memberService.deleteById(memberId);

        return "redirect:/dashboard/members";
    }
}
