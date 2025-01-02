package com.churchwebsite.churchwebsite.services.shopping;

import com.churchwebsite.churchwebsite.entities.shopping.Shipping;
import com.churchwebsite.churchwebsite.enums.ShippingStatus;
import com.churchwebsite.churchwebsite.repositories.Shopping.ShippingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ShippingService {

    @Autowired
    private ShippingRepository shippingRepository;

    public Page<Shipping> getAllShipments(int page, Integer pageSize, String sortBy) {

        Pageable pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.by(sortBy)));
        return shippingRepository.findAll(pageable);
    }

    public Optional<Shipping> getShipmentById(Integer id) {
        return shippingRepository.findById(id);
    }

    public Shipping saveShipment(Shipping shipping) {
        return shippingRepository.save(shipping);
    }

    public Shipping updateShipment(Shipping updatedShipping) {
        // Set the shipment and delivery dates based on the statuses
        if(updatedShipping.getStatus() == ShippingStatus.SHIPPED){
            updatedShipping.setShippedAt(LocalDateTime.now());
            updatedShipping.setDeliveredAt(null);
        }else if(updatedShipping.getStatus() == ShippingStatus.CANCELLED){
            updatedShipping.setShippedAt(null);
            updatedShipping.setDeliveredAt(null);
        }else if(updatedShipping.getStatus() == ShippingStatus.COLLECTED){
            updatedShipping.setShippedAt(LocalDateTime.now());
            updatedShipping.setDeliveredAt(LocalDateTime.now());
        }else if(updatedShipping.getStatus() == ShippingStatus.PENDING){
            updatedShipping.setShippedAt(null);
            updatedShipping.setDeliveredAt(null);
        }else if(updatedShipping.getStatus() == ShippingStatus.COMPLETED){
            if(updatedShipping.getShippedAt() == null){
                updatedShipping.setShippedAt(LocalDateTime.now());
            }
            updatedShipping.setDeliveredAt(LocalDateTime.now());
        }

        return shippingRepository.save(updatedShipping);
    }

    public void deleteShipment(Integer id) {
        shippingRepository.deleteById(id);
    }
}

