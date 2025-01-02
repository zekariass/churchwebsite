package com.churchwebsite.churchwebsite.repositories.Shopping;

import com.churchwebsite.churchwebsite.entities.shopping.Product;
import com.churchwebsite.churchwebsite.entities.shopping.ProductCategory;
import com.churchwebsite.churchwebsite.enums.ProductDeliveryType;
import com.churchwebsite.churchwebsite.enums.ProductListingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p " +
            "WHERE ((LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "        OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "       AND p.listingStatus = :listingStatus " +
            "       AND p.deliveryType LIKE CONCAT('%', :deliveryType, '%') " +
            "       AND p.stockQuantity > 0)")
    Page<Product> findAllListedProductsBySearchParams(@Param("keyword") String keyword,
                                                      @Param("listingStatus") ProductListingStatus listingStatus,
                                                      @Param("deliveryType") ProductDeliveryType deliveryType,
                                                      Pageable pageable);


    Page<Product> findByListingStatusAndStockQuantityGreaterThan(
            ProductListingStatus productListingStatus,
            int quantity,
            Pageable pageable);

    Page<Product> findByCategory(ProductCategory category, Pageable pageable);

    Page<Product> findByCategoryAndListingStatusAndStockQuantityGreaterThan(
            ProductCategory productCategory,
            ProductListingStatus productListingStatus,
            int quantity,
            Pageable pageable);
}

