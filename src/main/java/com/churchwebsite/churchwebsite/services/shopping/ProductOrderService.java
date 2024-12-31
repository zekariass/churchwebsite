package com.churchwebsite.churchwebsite.services.shopping;

import com.churchwebsite.churchwebsite.entities.shopping.Orders;
import com.churchwebsite.churchwebsite.repositories.Shopping.ProductOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductOrderService {

    @Autowired
    private ProductOrderRepository productOrderRepository;

    public List<Orders> getAllOrders() {
        return productOrderRepository.findAll();
    }

    public Optional<Orders> getOrderById(Integer id) {
        return productOrderRepository.findById(id);
    }

    public Orders saveOrder(Orders orders) {
        return productOrderRepository.save(orders);
    }

    public Orders updateOrder(Integer id, Orders updatedOrder) {
        return productOrderRepository.findById(id)
                .map(existingOrder -> {
                    existingOrder.setUser(updatedOrder.getUser());
                    existingOrder.setTotalPrice(updatedOrder.getTotalPrice());
                    existingOrder.setShippingPrice(updatedOrder.getShippingPrice());
                    existingOrder.setTotalQuantity(updatedOrder.getTotalQuantity());
                    existingOrder.setStatus(updatedOrder.getStatus());
                    return productOrderRepository.save(existingOrder);
                }).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public void deleteOrder(Integer id) {
        productOrderRepository.deleteById(id);
    }
}

