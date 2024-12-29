package com.churchwebsite.churchwebsite.repositories.Shopping;

import com.churchwebsite.churchwebsite.entities.shopping.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Custom query methods (if needed) can be added here
}

