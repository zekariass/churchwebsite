package com.churchwebsite.churchwebsite.services.shopping;

import com.churchwebsite.churchwebsite.entities.shopping.ProductOrder;
import com.churchwebsite.churchwebsite.repositories.Shopping.ProductOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductOrderService {

    @Autowired
    private ProductOrderRepository productOrderRepository;

    public List<ProductOrder> getAllOrders() {
        return productOrderRepository.findAll();
    }

    public Optional<ProductOrder> getOrderById(Integer id) {
        return productOrderRepository.findById(id);
    }

    public ProductOrder saveOrder(ProductOrder productOrder) {
        return productOrderRepository.save(productOrder);
    }

    public ProductOrder updateOrder(Integer id, ProductOrder updatedOrder) {
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

