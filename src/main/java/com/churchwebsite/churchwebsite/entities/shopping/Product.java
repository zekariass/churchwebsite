package com.churchwebsite.churchwebsite.entities.shopping;

import com.churchwebsite.churchwebsite.enums.ImageType;
import com.churchwebsite.churchwebsite.enums.ProductDeliveryType;
import com.churchwebsite.churchwebsite.enums.ProductListingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_type", columnDefinition = "ENUM('COLLECT', 'DELIVERY', 'DELIVERY_AND_COLLECT')")
    private ProductDeliveryType deliveryType = ProductDeliveryType.COLLECT;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ProductImage> images;

    @Enumerated(EnumType.STRING)
    private ProductListingStatus listingStatus;

    @Column(name = "weight_in_kg")
    private double weightInKg;

    @Version
    private Long version;


    public List<ProductImage> getThumbnailImages() {
        List<ProductImage> thumbs = new ArrayList<>();
        for(ProductImage image: images){
            if(image.getImageType() == ImageType.THUMBNAIL){
                thumbs.add(image);
            }
        }
        return thumbs;
    }

    public List<ProductImage> getGalleryImages() {
        List<ProductImage> galleries = new ArrayList<>();
        for(ProductImage image: images){
            if(image.getImageType() == ImageType.GALLERY){
                galleries.add(image);
            }
        }
        return galleries;
    }


}