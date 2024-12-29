package com.churchwebsite.churchwebsite.controllers.Shopping;

import com.churchwebsite.churchwebsite.entities.shopping.Shipping;
import com.churchwebsite.churchwebsite.services.shopping.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/shippings")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    @GetMapping
    public String getAllShippings(Model model) {
        List<Shipping> shippings = shippingService.getAllShipments();
        model.addAttribute("shippings", shippings);
        return "shipping/list";
    }

    @GetMapping("/{id}")
    public String getShippingById(@PathVariable Integer id, Model model) {
        model.addAttribute("shipping", shippingService.getShipmentById(id).orElse(null));
        return "shipping/detail";
    }

    @PostMapping
    public String createShipping(@ModelAttribute Shipping shipping) {
        shippingService.saveShipment(shipping);
        return "redirect:/shippings";
    }

    @PutMapping("/{id}")
    public String updateShipping(@PathVariable Integer id, @ModelAttribute Shipping updatedShipping) {
        shippingService.updateShipment(id, updatedShipping);
        return "redirect:/shippings";
    }

    @DeleteMapping("/{id}")
    public String deleteShipping(@PathVariable Integer id) {
        shippingService.deleteShipment(id);
        return "redirect:/shippings";
    }
}
