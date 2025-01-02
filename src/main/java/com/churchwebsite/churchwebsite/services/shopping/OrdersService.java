package com.churchwebsite.churchwebsite.services.shopping;

import com.churchwebsite.churchwebsite.entities.shopping.OrderItem;
import com.churchwebsite.churchwebsite.entities.shopping.Orders;
import com.churchwebsite.churchwebsite.entities.shopping.Shipping;
import com.churchwebsite.churchwebsite.enums.OrderStatus;
import com.churchwebsite.churchwebsite.repositories.Shopping.OrderItemRepository;
import com.churchwebsite.churchwebsite.repositories.Shopping.OrderRepository;
import com.churchwebsite.churchwebsite.repositories.Shopping.ShippingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdersService {


    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShippingRepository shippingRepository;

    @Autowired
    public OrdersService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ShippingRepository shippingRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.shippingRepository = shippingRepository;
    }

    public Page<Orders> getAllOrders(int page, Integer pageSize, String sortBy) {
        if(sortBy.isEmpty()){
            sortBy = "createdAt";
        }

        Pageable pageable;
        if(sortBy.equals("createdAt")){
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.desc(sortBy)));
        }else{
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        }
        return orderRepository.findAll(pageable);
    }

    public Optional<Orders> getOrderById(Integer id) {
        return orderRepository.findById(id);
    }

    public Orders saveOrder(Orders newOrder) {
        if(newOrder.getOrderId() != null){
            Orders oldOrder = orderRepository.findById(newOrder.getOrderId()).orElse(null);
            if (oldOrder != null && oldOrder.getStatus() != newOrder.getStatus()) {
                if (newOrder.getStatus() == OrderStatus.COMPLETED) {
                    List<OrderItem> orderItems = orderItemRepository.findAllByOrder(newOrder);
                    for (OrderItem orderItem : orderItems) {
                        orderItem.setStatus(OrderStatus.COMPLETED);
                        orderItemRepository.save(orderItem);
                    }
                } else if (newOrder.getStatus() == OrderStatus.CANCELLED) {
                    List<OrderItem> orderItems = orderItemRepository.findAllByOrder(newOrder);
                    for (OrderItem orderItem : orderItems) {
                        orderItem.setStatus(OrderStatus.CANCELLED);
                        orderItemRepository.save(orderItem);
                    }
                } else if (newOrder.getStatus() == OrderStatus.SUBMITTED) {
                    List<OrderItem> orderItems = orderItemRepository.findAllByOrder(newOrder);
                    for (OrderItem orderItem : orderItems) {
                        orderItem.setStatus(OrderStatus.SUBMITTED);
                        orderItemRepository.save(orderItem);
                    }
                }
            }
        }

        return orderRepository.save(newOrder);
    }

    public Orders updateOrder(Integer id, Orders updatedOrder) {
        return orderRepository.findById(id)
                .map(existingOrder -> {
                    existingOrder.setUser(updatedOrder.getUser());
                    existingOrder.setShipping(updatedOrder.getShipping());
                    existingOrder.setTotalPrice(updatedOrder.getTotalPrice());
                    existingOrder.setShippingPrice(updatedOrder.getShippingPrice());
                    existingOrder.setTotalQuantity(updatedOrder.getTotalQuantity());
                    existingOrder.setStatus(updatedOrder.getStatus());
                    return orderRepository.save(existingOrder);
                }).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }

    public List<Orders> getOrdersByShippingId(Integer shippingId) {
        Shipping shipping = shippingRepository.findById(shippingId).orElse(null);
        return orderRepository.findByShipping(shipping);
    }
}

