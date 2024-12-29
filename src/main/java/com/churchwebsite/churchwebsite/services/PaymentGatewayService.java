package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.PaymentGateway;
import com.churchwebsite.churchwebsite.repositories.PaymentGatewayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentGatewayService {

    @Autowired
    private PaymentGatewayRepository paymentGatewayRepository;

    // Create or Update
    public PaymentGateway savePaymentGateway(PaymentGateway paymentGateway) {
        return paymentGatewayRepository.save(paymentGateway);
    }

    // Read All
    public List<PaymentGateway> getAllPaymentGateways() {
        return paymentGatewayRepository.findAll();
    }

    // Read by ID
    public Optional<PaymentGateway> getPaymentGatewayById(int id) {
        return paymentGatewayRepository.findById(id);
    }

    // Delete
    public void deletePaymentGateway(int id) {
        paymentGatewayRepository.deleteById(id);
    }
}
