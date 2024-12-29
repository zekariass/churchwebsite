package com.churchwebsite.churchwebsite.repositories.Shopping;

import com.churchwebsite.churchwebsite.entities.shopping.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    Page<ProductImage> findByProduct(Integer productId, Pageable pageable);
}
