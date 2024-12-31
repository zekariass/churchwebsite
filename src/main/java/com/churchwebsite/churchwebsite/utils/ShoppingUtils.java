package com.churchwebsite.churchwebsite.utils;

import com.churchwebsite.churchwebsite.entities.shopping.CartItem;
import com.churchwebsite.churchwebsite.enums.DeliveryType;

import java.util.List;

public class ShoppingUtils {



    public static double getCartSubtotal(List<CartItem> cartItems){
        double totalPrice = 0.0;
        for(CartItem cartItem: cartItems){
            totalPrice += cartItem.getQuantity() * cartItem.getProduct().getPrice().doubleValue();
        }

        return totalPrice;
    }

    public static double computeShippingPrice(double miles, double weightInKg, double basePrice, double perMilePrice, double perKgPrice){
        return basePrice + weightInKg * perMilePrice + miles * perKgPrice;
    }

    public static double getTotalWeightsForCart(List<CartItem> cartItems){
        double totalWeight = 0;
        for(CartItem cartItem: cartItems){
            if(cartItem.getDeliveryType() == DeliveryType.DELIVERY){
                totalWeight += cartItem.getProduct().getWeightInKg();
            }
        }

        return totalWeight;
    }
}
