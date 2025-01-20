package com.churchwebsite.churchwebsite.utils;

import com.churchwebsite.churchwebsite.dtos.CartItemDTO;
import com.churchwebsite.churchwebsite.entities.shopping.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

public class CookieUtils {

    private static final String CART_COOKIE_NAME = "cart";
    private static final String CART_COOKIE_PATH = "/shopping";

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .findAndRegisterModules()
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

    public static List<CartItemDTO> getCartFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (CART_COOKIE_NAME.equals(cookie.getName())) {

                    try {
                        String decodedJson = new String(Base64.getDecoder().decode(cookie.getValue()));

                        return objectMapper.readValue(decodedJson, new TypeReference<List<CartItemDTO>>() {
                        });
                    } catch (Exception e) {
                        // Log the error and return an empty cart
                        System.err.println("Error reading cart from cookies: " + e.getMessage());
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    public static void saveCartToCookies(List<CartItemDTO> cartItems, Integer cookieLifetime, HttpServletResponse response) {
        try {
            String json = objectMapper.writeValueAsString(cartItems);
            if (json.length() > 4096) {
                throw new IllegalArgumentException("Cart data exceeds cookie size limit");
            }
            // Encode mapped cart data to Base64 for safe save in cookie, otherwise special characters are not supported by cookie
            String encodedJson = Base64.getEncoder().encodeToString(json.getBytes());

            Cookie cookie = new Cookie(CART_COOKIE_NAME, encodedJson);
            cookie.setPath(CART_COOKIE_PATH);
            cookie.setHttpOnly(true); // Security enhancement
            cookie.setSecure(false);  // Requires HTTPS

            if (cookieLifetime != null) {
                cookie.setMaxAge(cookieLifetime);
            } else {
                cookie.setMaxAge(7 * 24 * 60 * 60);
            }

            response.addCookie(cookie);
        } catch (Exception e) {
            // Log the error
            System.err.println("Error saving cart to cookies: " + e.getMessage());
        }
    }

    public static void clearCartCookies(HttpServletResponse response) {
        Cookie cookie = new Cookie(CART_COOKIE_NAME, null);
        cookie.setPath(CART_COOKIE_PATH);
        cookie.setHttpOnly(true); // Security enhancement
        cookie.setSecure(false);  // Requires HTTPS
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }


    public static void incrementCartItemQuantity( Product product, HttpServletRequest request, HttpServletResponse response, Integer cookieLifeTime) {
        if (request.getCookies() != null) {
            List<CartItemDTO> cartItemDTOs = getCartFromCookies(request);
            for (CartItemDTO dto : cartItemDTOs) {
                // If it finds the cartItem with cartItemId in cookie and if the quantity is less than the product stockQuantity, increment it
                if (Objects.equals(dto.getProductId(), product.getProductId()) && dto.getQuantity() < product.getStockQuantity()) {
                    dto.setQuantity(dto.getQuantity() + 1);
                    break;
                }
            }

            saveCartToCookies(cartItemDTOs, cookieLifeTime, response);
        }
    }

    public static void decrementCartItemQuantity(Product product, HttpServletRequest request, HttpServletResponse response, Integer cookieLifeTime) {
        if (request.getCookies() != null) {
            List<CartItemDTO> cartItemDTOs = getCartFromCookies(request);
            for (CartItemDTO dto : cartItemDTOs) {
                // If the cartItem is found in cookie, and its quantity is greater than one, decrement it
                if (Objects.equals(product.getProductId(), dto.getProductId()) && dto.getQuantity() > 1) {
                    dto.setQuantity(dto.getQuantity()  - 1);
                    break;
                }
            }

            saveCartToCookies(cartItemDTOs, cookieLifeTime, response);
        }

    }

    public static void deleteCartItemFromCookie(Product product, HttpServletRequest request, HttpServletResponse response, Integer cookieLifeTime) {
        if (request.getCookies() != null) {
            List<CartItemDTO> cartItemDTOs = getCartFromCookies(request);
            for (CartItemDTO dto : cartItemDTOs) {
                // If the cartItem is found in cookie, and its quantity is greater than one, decrement it
                if (Objects.equals(dto.getProductId(), product.getProductId())) {
                    cartItemDTOs.remove(dto);
                    break;
                }

            }

            saveCartToCookies(cartItemDTOs, cookieLifeTime, response);
        }

    }
}
