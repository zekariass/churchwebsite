package com.churchwebsite.churchwebsite.controllers.Shopping;

import com.churchwebsite.churchwebsite.entities.shopping.OrderItem;
import com.churchwebsite.churchwebsite.services.shopping.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping
    public String getAllOrderItems(Model model) {
        List<OrderItem> orderItems = orderItemService.getAllOrderItems();
        model.addAttribute("orderItems", orderItems);
        return "order-item/list";
    }

    @GetMapping("/{id}")
    public String getOrderItemById(@PathVariable Integer id, Model model) {
        model.addAttribute("orderItem", orderItemService.getOrderItemById(id).orElse(null));
        return "order-item/detail";
    }

    @PostMapping
    public String createOrderItem(@ModelAttribute OrderItem orderItem) {
        orderItemService.saveOrderItem(orderItem);
        return "redirect:/order-items";
    }

    @PutMapping("/{id}")
    public String updateOrderItem(@PathVariable Integer id, @ModelAttribute OrderItem updatedOrderItem) {
        orderItemService.updateOrderItem(id, updatedOrderItem);
        return "redirect:/order-items";
    }

    @DeleteMapping("/{id}")
    public String deleteOrderItem(@PathVariable Integer id) {
        orderItemService.deleteOrderItem(id);
        return "redirect:/order-items";
    }
}
