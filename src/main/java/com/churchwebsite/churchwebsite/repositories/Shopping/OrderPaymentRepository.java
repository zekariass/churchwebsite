package com.churchwebsite.churchwebsite.repositories.Shopping;

import com.churchwebsite.churchwebsite.entities.shopping.OrderPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPaymentRepository extends JpaRepository<OrderPayment, Integer> {}
