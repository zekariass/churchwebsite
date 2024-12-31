package com.churchwebsite.churchwebsite.dtos;

import com.churchwebsite.churchwebsite.enums.DeliveryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {

    private Integer cartItemId;
    private Integer cartId;
    private Integer productId;
    private Integer quantity;
    private DeliveryType deliveryType;
}
