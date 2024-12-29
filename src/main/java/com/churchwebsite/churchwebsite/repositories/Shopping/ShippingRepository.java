package com.churchwebsite.churchwebsite.repositories.Shopping;

import com.churchwebsite.churchwebsite.entities.shopping.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping, Integer> {}
