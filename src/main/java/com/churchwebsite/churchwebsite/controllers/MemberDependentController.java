package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.services.MemberDependentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("dashboard/members/dependents")
public class MemberDependentController {
    private final MemberDependentService memberDependentService;

    @Autowired
    public MemberDependentController(MemberDependentService memberDependentService) {
        this.memberDependentService = memberDependentService;
    }
}
