package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.PaymentGateway;
import com.churchwebsite.churchwebsite.services.PaymentGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/payment-gateways")
public class PaymentGatewayController {

    @Autowired
    private PaymentGatewayService paymentGatewayService;

    @GetMapping
    public String getAllPaymentGateways(Model model) {
        List<PaymentGateway> paymentGateways = paymentGatewayService.getAllPaymentGateways();
        model.addAttribute("paymentGateways", paymentGateways);
        return "payment-gateway/list";
    }

    @GetMapping("/{id}")
    public String getPaymentGatewayById(@PathVariable int id, Model model) {
        model.addAttribute("paymentGateway", paymentGatewayService.getPaymentGatewayById(id).orElse(null));
        return "payment-gateway/detail";
    }

    @PostMapping
    public String createPaymentGateway(@ModelAttribute PaymentGateway paymentGateway) {
        paymentGatewayService.savePaymentGateway(paymentGateway);
        return "redirect:/payment-gateways";
    }

    @PutMapping("/{id}")
    public String updatePaymentGateway(@PathVariable int id, @ModelAttribute PaymentGateway updatedPaymentGateway) {
        PaymentGateway existingPaymentGateway = paymentGatewayService.getPaymentGatewayById(id).orElse(null);
        if (existingPaymentGateway != null) {
            updatedPaymentGateway.setPaymentGatewayId(id);
            paymentGatewayService.savePaymentGateway(updatedPaymentGateway);
        }
        return "redirect:/payment-gateways";
    }

    @DeleteMapping("/{id}")
    public String deletePaymentGateway(@PathVariable int id) {
        paymentGatewayService.deletePaymentGateway(id);
        return "redirect:/payment-gateways";
    }
}
