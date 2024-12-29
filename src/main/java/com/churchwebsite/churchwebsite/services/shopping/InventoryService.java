package com.churchwebsite.churchwebsite.services.shopping;

import com.churchwebsite.churchwebsite.entities.shopping.Inventory;
import com.churchwebsite.churchwebsite.repositories.Shopping.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    public Optional<Inventory> getInventoryById(Integer id) {
        return inventoryRepository.findById(id);
    }

    public Inventory saveInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public Inventory updateInventory(Integer id, Inventory updatedInventory) {
        return inventoryRepository.findById(id)
                .map(existingInventory -> {
                    existingInventory.setStockQuantity(updatedInventory.getStockQuantity());
                    existingInventory.setProduct(updatedInventory.getProduct());
                    return inventoryRepository.save(existingInventory);
                }).orElseThrow(() -> new RuntimeException("Inventory not found"));
    }

    public void deleteInventory(Integer id) {
        inventoryRepository.deleteById(id);
    }
}

