package com.churchwebsite.churchwebsite.repositories.Shopping;

import com.churchwebsite.churchwebsite.entities.shopping.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {}
