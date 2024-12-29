package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.PaymentGateway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentGatewayRepository extends JpaRepository<PaymentGateway, Integer> {
}
