package com.churchwebsite.churchwebsite.dtos;

import com.churchwebsite.churchwebsite.entities.Address;
import com.churchwebsite.churchwebsite.entities.shopping.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutDTO {
    private Address address;
    private double totalPrice;
    private double shippingPrice;
    private double totalTax;
    private List<CartItem> cartItems;
    private int cartId;
}
