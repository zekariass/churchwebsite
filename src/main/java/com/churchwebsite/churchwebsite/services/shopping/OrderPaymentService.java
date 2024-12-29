package com.churchwebsite.churchwebsite.services.shopping;

import com.churchwebsite.churchwebsite.entities.shopping.OrderPayment;
import com.churchwebsite.churchwebsite.repositories.Shopping.OrderPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderPaymentService {

    @Autowired
    private OrderPaymentRepository orderPaymentRepository;

    public List<OrderPayment> getAllPayments() {
        return orderPaymentRepository.findAll();
    }

    public Optional<OrderPayment> getPaymentById(Integer id) {
        return orderPaymentRepository.findById(id);
    }

    public OrderPayment savePayment(OrderPayment orderPayment) {
        return orderPaymentRepository.save(orderPayment);
    }

    public OrderPayment updatePayment(Integer id, OrderPayment updatedPayment) {
        return orderPaymentRepository.findById(id)
                .map(existingPayment -> {
                    existingPayment.setOrder(updatedPayment.getOrder());
                    existingPayment.setUser(updatedPayment.getUser());
                    existingPayment.setAmount(updatedPayment.getAmount());
                    existingPayment.setPaymentMethod(updatedPayment.getPaymentMethod());
                    existingPayment.setStatus(updatedPayment.getStatus());
                    return orderPaymentRepository.save(existingPayment);
                }).orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    public void deletePayment(Integer id) {
        orderPaymentRepository.deleteById(id);
    }
}
