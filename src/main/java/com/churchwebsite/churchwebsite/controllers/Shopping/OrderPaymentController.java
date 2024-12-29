package com.churchwebsite.churchwebsite.controllers.Shopping;

import com.churchwebsite.churchwebsite.entities.shopping.OrderPayment;
import com.churchwebsite.churchwebsite.services.shopping.OrderPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/payments")
public class OrderPaymentController {

    @Autowired
    private OrderPaymentService orderPaymentService;

    @GetMapping
    public String getAllPayments(Model model) {
        List<OrderPayment> payments = orderPaymentService.getAllPayments();
        model.addAttribute("payments", payments);
        return "payment/list";
    }

    @GetMapping("/{id}")
    public String getPaymentById(@PathVariable Integer id, Model model) {
        model.addAttribute("payment", orderPaymentService.getPaymentById(id).orElse(null));
        return "payment/detail";
    }

    @PostMapping
    public String createPayment(@ModelAttribute OrderPayment orderPayment) {
        orderPaymentService.savePayment(orderPayment);
        return "redirect:/payments";
    }

    @PutMapping("/{id}")
    public String updatePayment(@PathVariable Integer id, @ModelAttribute OrderPayment updatedPayment) {
        orderPaymentService.updatePayment(id, updatedPayment);
        return "redirect:/payments";
    }

    @DeleteMapping("/{id}")
    public String deletePayment(@PathVariable Integer id) {
        orderPaymentService.deletePayment(id);
        return "redirect:/payments";
    }
}
