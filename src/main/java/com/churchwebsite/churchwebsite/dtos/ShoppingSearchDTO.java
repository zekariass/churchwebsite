package com.churchwebsite.churchwebsite.dtos;

import com.churchwebsite.churchwebsite.enums.ProductDeliveryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingSearchDTO {
    private String keyword;
    private ProductDeliveryType deliveryType;
}
