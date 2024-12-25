package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.services.MemberSpouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("dashboard/member/spouse")
public class MemberSpouseController {
    private final MemberSpouseService memberSpouseService;

    @Autowired
    public MemberSpouseController(MemberSpouseService memberSpouseService) {
        this.memberSpouseService = memberSpouseService;
    }
}
