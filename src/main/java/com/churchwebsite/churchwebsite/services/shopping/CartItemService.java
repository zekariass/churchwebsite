package com.churchwebsite.churchwebsite.services.shopping;

import com.churchwebsite.churchwebsite.dtos.CartItemDTO;
import com.churchwebsite.churchwebsite.entities.shopping.Cart;
import com.churchwebsite.churchwebsite.entities.shopping.CartItem;
import com.churchwebsite.churchwebsite.entities.shopping.Product;
import com.churchwebsite.churchwebsite.enums.DeliveryType;
import com.churchwebsite.churchwebsite.repositories.Shopping.CartItemRepository;
import com.churchwebsite.churchwebsite.repositories.Shopping.CartRepository;
import com.churchwebsite.churchwebsite.services.SettingsService;
import com.churchwebsite.churchwebsite.services.UserService;
import com.churchwebsite.churchwebsite.utils.CookieUtils;
import com.churchwebsite.churchwebsite.utils.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final UserService userService;
    private final SettingsService settingsService;

    public CartItemService(CartItemRepository cartItemRepository, CartRepository cartRepository, ProductService productService, UserService userService, SettingsService settingsService) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.userService = userService;
        this.settingsService = settingsService;
    }

    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    public Optional<CartItem> getCartItemById(Integer id) {
        return cartItemRepository.findById(id);
    }

    public CartItem saveCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public CartItem updateCartItem(Integer id, CartItem updatedCartItem) {
        return cartItemRepository.findById(id)
                .map(existingCartItem -> {
                    existingCartItem.setCart(updatedCartItem.getCart());
                    existingCartItem.setProduct(updatedCartItem.getProduct());
                    existingCartItem.setQuantity(updatedCartItem.getQuantity());
                    return cartItemRepository.save(existingCartItem);
                }).orElseThrow(() -> new RuntimeException("Cart Item not found"));
    }

    public CartItemDTO mapToDTO(CartItem cartItem){

        CartItemDTO cartItemDTO = new CartItemDTO();

        cartItemDTO.setCartItemId(cartItem.getCartItemId());
        cartItemDTO.setCartId(cartItem.getCart() != null? cartItem.getCart().getCartId(): 0);
        cartItemDTO.setProductId(cartItem.getProduct().getProductId());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setDeliveryType(cartItem.getDeliveryType());

        return cartItemDTO;
    }

    public CartItem mapToCartItem(CartItemDTO dto){
        CartItem cartItem = new CartItem();

        cartItem.setCartItemId(dto.getCartId());
        cartItem.setCart(cartRepository.findById(dto.getCartId()).orElse(null));
        cartItem.setProduct(productService.getProductById(dto.getProductId()).orElse(null));
        cartItem.setQuantity(dto.getQuantity());
        cartItem.setDeliveryType(dto.getDeliveryType());

        return cartItem;
    }

    public CartItem findByCart(Cart cart) {
        return cartItemRepository.findByCart(cart);
    }

    public CartItem findByCartAndProduct(Cart cart, Product product) {
        return cartItemRepository.findByCartAndProduct(cart, product);
    }

    public void incrementQuantity(Integer cartItemId, Integer productId, HttpServletRequest request, HttpServletResponse response) {
        CustomUserDetails userDetails = userService.getCurrentUser();

        if(userDetails != null){
            // Update cart item in DB
            CartItem cartItem = cartItemRepository.findById(cartItemId).orElse(null);

            // If cartItem is not null and its quantity is less than the stock-quantity, increment it
            if(cartItem != null && cartItem.getQuantity() < cartItem.getProduct().getStockQuantity()){
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                cartItemRepository.save(cartItem);
            }
        }else {
            // Update cart in Cookie
            Product product = productService.getProductById(productId).orElse(null);
            Integer cookieLifeTime = settingsService.findBySettingName("CART_COOKIE_LIFETIME").getSettingValueInt();
            CookieUtils.incrementCartItemQuantity(product, request, response, cookieLifeTime);
        }
    }

    public void decrementQuantity(Integer cartItemId, Integer productId, HttpServletRequest request, HttpServletResponse response) {
        CustomUserDetails userDetails = userService.getCurrentUser();

        if(userDetails != null){
            // Update cart item in DB
            CartItem cartItem = cartItemRepository.findById(cartItemId).orElse(null);
            if(cartItem != null && cartItem.getQuantity() > 1){
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                cartItemRepository.save(cartItem);
            }
        }else {
            // Update cart in Cookie
            Integer cookieLifeTime = settingsService.findBySettingName("CART_COOKIE_LIFETIME").getSettingValueInt();
            Product product = productService.getProductById(productId).orElse(null);
            CookieUtils.decrementCartItemQuantity(product, request, response, cookieLifeTime);
        }
    }

    public void deleteCartItem(Integer cartItemId, Integer productId, HttpServletRequest request, HttpServletResponse response) {

        CustomUserDetails userDetails = userService.getCurrentUser();
        if(userDetails != null){
            // Delete from DB
            cartItemRepository.deleteById(cartItemId);
        }else {
            // Delete from Cookie
            Integer cookieLifeTime = settingsService.findBySettingName("CART_COOKIE_LIFETIME").getSettingValueInt();
            Product product = productService.getProductById(productId).orElse(null);
            CookieUtils.deleteCartItemFromCookie(product, request, response, cookieLifeTime);

        }


    }

    public void updateCartItemDeliveryType(Integer cartItemId, DeliveryType deliveryType) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElse(null);

        if(cartItem != null){
            cartItem.setDeliveryType(deliveryType);
            cartItemRepository.save(cartItem);
        }
    }
}

