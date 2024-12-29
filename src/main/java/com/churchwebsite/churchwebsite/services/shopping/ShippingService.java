package com.churchwebsite.churchwebsite.services.shopping;

import com.churchwebsite.churchwebsite.entities.shopping.Shipping;
import com.churchwebsite.churchwebsite.repositories.Shopping.ShippingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShippingService {

    @Autowired
    private ShippingRepository shippingRepository;

    public List<Shipping> getAllShipments() {
        return shippingRepository.findAll();
    }

    public Optional<Shipping> getShipmentById(Integer id) {
        return shippingRepository.findById(id);
    }

    public Shipping saveShipment(Shipping shipping) {
        return shippingRepository.save(shipping);
    }

    public Shipping updateShipment(Integer id, Shipping updatedShipping) {
        return shippingRepository.findById(id)
                .map(existingShipping -> {
                    existingShipping.setOrderItem(updatedShipping.getOrderItem());
                    existingShipping.setAddress(updatedShipping.getAddress());
                    existingShipping.setDeliveryDate(updatedShipping.getDeliveryDate());
                    existingShipping.setStatus(updatedShipping.getStatus());
                    existingShipping.setShippedAt(updatedShipping.getShippedAt());
                    existingShipping.setDeliveredAt(updatedShipping.getDeliveredAt());
                    return shippingRepository.save(existingShipping);
                }).orElseThrow(() -> new RuntimeException("Shipment not found"));
    }

    public void deleteShipment(Integer id) {
        shippingRepository.deleteById(id);
    }
}

