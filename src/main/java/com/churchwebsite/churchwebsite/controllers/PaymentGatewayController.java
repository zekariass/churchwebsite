package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.PaymentGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payment-gateways")
public class PaymentGatewayController {

    private final PaymentGatewayService paymentGatewayService;
    private final ChurchDetailService churchDetailService;

    @Autowired
    public PaymentGatewayController(PaymentGatewayService paymentGatewayService, ChurchDetailService churchDetailService) {
        this.paymentGatewayService = paymentGatewayService;
        this.churchDetailService = churchDetailService;
    }

}
