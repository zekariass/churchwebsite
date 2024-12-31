package com.churchwebsite.churchwebsite.controllers.Shopping;

import com.churchwebsite.churchwebsite.entities.shopping.Orders;
import com.churchwebsite.churchwebsite.services.shopping.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class ProductOrderController {

    @Autowired
    private ProductOrderService productOrderService;

    @GetMapping
    public String getAllOrders(Model model) {
        List<Orders> orders = productOrderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "order/list";
    }

    @GetMapping("/{id}")
    public String getOrderById(@PathVariable Integer id, Model model) {
        model.addAttribute("order", productOrderService.getOrderById(id).orElse(null));
        return "order/detail";
    }

    @PostMapping
    public String createOrder(@ModelAttribute Orders orders) {
        productOrderService.saveOrder(orders);
        return "redirect:/orders";
    }

    @PutMapping("/{id}")
    public String updateOrder(@PathVariable Integer id, @ModelAttribute Orders updatedOrder) {
        productOrderService.updateOrder(id, updatedOrder);
        return "redirect:/orders";
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Integer id) {
        productOrderService.deleteOrder(id);
        return "redirect:/orders";
    }
}

