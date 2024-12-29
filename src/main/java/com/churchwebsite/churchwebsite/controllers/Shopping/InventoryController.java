package com.churchwebsite.churchwebsite.controllers.Shopping;

import com.churchwebsite.churchwebsite.entities.shopping.Inventory;
import com.churchwebsite.churchwebsite.services.shopping.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public String getAllInventories(Model model) {
        List<Inventory> inventories = inventoryService.getAllInventories();
        model.addAttribute("inventories", inventories);
        return "inventory/list";
    }

    @GetMapping("/{id}")
    public String getInventoryById(@PathVariable Integer id, Model model) {
        model.addAttribute("inventory", inventoryService.getInventoryById(id).orElse(null));
        return "inventory/detail";
    }

    @PostMapping
    public String createInventory(@ModelAttribute Inventory inventory) {
        inventoryService.saveInventory(inventory);
        return "redirect:/inventories";
    }

    @PutMapping("/{id}")
    public String updateInventory(@PathVariable Integer id, @ModelAttribute Inventory updatedInventory) {
        inventoryService.updateInventory(id, updatedInventory);
        return "redirect:/inventories";
    }

    @DeleteMapping("/{id}")
    public String deleteInventory(@PathVariable Integer id) {
        inventoryService.deleteInventory(id);
        return "redirect:/inventories";
    }
}

