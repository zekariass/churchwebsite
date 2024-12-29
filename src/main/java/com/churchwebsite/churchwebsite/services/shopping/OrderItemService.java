package com.churchwebsite.churchwebsite.services.shopping;

import com.churchwebsite.churchwebsite.entities.shopping.OrderItem;
import com.churchwebsite.churchwebsite.repositories.Shopping.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public Optional<OrderItem> getOrderItemById(Integer id) {
        return orderItemRepository.findById(id);
    }

    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public OrderItem updateOrderItem(Integer id, OrderItem updatedOrderItem) {
        return orderItemRepository.findById(id)
                .map(existingOrderItem -> {
                    existingOrderItem.setOrder(updatedOrderItem.getOrder());
                    existingOrderItem.setProduct(updatedOrderItem.getProduct());
                    existingOrderItem.setQuantity(updatedOrderItem.getQuantity());
                    existingOrderItem.setPrice(updatedOrderItem.getPrice());
                    existingOrderItem.setTotalPrice(updatedOrderItem.getTotalPrice());
                    return orderItemRepository.save(existingOrderItem);
                }).orElseThrow(() -> new RuntimeException("Order Item not found"));
    }

    public void deleteOrderItem(Integer id) {
        orderItemRepository.deleteById(id);
    }
}

