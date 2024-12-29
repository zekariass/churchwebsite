package com.churchwebsite.churchwebsite.services.shopping;

import com.churchwebsite.churchwebsite.dtos.CartItemDTO;
import com.churchwebsite.churchwebsite.entities.Settings;
import com.churchwebsite.churchwebsite.entities.shopping.Cart;
import com.churchwebsite.churchwebsite.entities.shopping.CartItem;
import com.churchwebsite.churchwebsite.repositories.Shopping.CartItemRepository;
import com.churchwebsite.churchwebsite.repositories.Shopping.CartRepository;
import com.churchwebsite.churchwebsite.services.SettingsService;
import com.churchwebsite.churchwebsite.services.UserService;
import com.churchwebsite.churchwebsite.utils.CookieUtils;
import com.churchwebsite.churchwebsite.utils.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {


    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final SettingsService settingsService;
    private final CartItemService cartItemService;

    @Autowired
    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       UserService userService,
                       SettingsService settingsService,
                       CartItemService cartItemService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;
        this.settingsService = settingsService;
        this.cartItemService = cartItemService;
    }

    public List<CartItem> getCart(HttpServletRequest request, HttpServletResponse response) {

        CustomUserDetails userDetails = userService.getCurrentUser();

        // Check if the user is logged in. Merge cookie cart to db if there cart in cookie
        if (userDetails != null) {
            mergeCart(request, response);

            // Logged-in user: Fetch cart from the database
            Optional<Cart> optionalCart = cartRepository.findByUser(userDetails.getUser());
            return optionalCart.isPresent() ? optionalCart.get().getItems() : new ArrayList<>();
        } else {
            // Guest user: Fetch cart from cookies
            List<CartItem> cartItems = new ArrayList<>();

            // Convert DTO to CartItem since DTOs are saved in Cookie
            for(CartItemDTO dto: CookieUtils.getCartFromCookies(request)){
                CartItem cartItem = cartItemService.mapToCartItem(dto);
                cartItems.add(cartItem);
            }
            return cartItems;
        }
    }

    public void addToCart(HttpServletRequest request, HttpServletResponse response, CartItem newItem) {
        CustomUserDetails userDetails = userService.getCurrentUser();

        if (userDetails != null) {
            // Logged-in user: Add to database cart
            Cart cart = cartRepository.findByUser(userDetails.getUser()).orElse(null);
            if (cart == null) {
                cart = new Cart();
                cart.setUser(userDetails.getUser()); // Assuming a User entity
                cart = cartRepository.save(cart);
            }
            newItem.setCart(cart);

            // Check if the cartItem is already present in db. If so, simply update the quantity
            CartItem dbCartItem = cartItemService.findByCartAndProduct(cart, newItem.getProduct());

            if(dbCartItem != null){
                dbCartItem.setQuantity(dbCartItem.getQuantity() + newItem.getQuantity());
            }else{
                dbCartItem = newItem;
            }
            cartItemRepository.save(dbCartItem);

        } else {
            // Guest user: Add to cookie-based cart
            CartItemDTO cartItemDTO = cartItemService.mapToDTO(newItem);

            List<CartItemDTO> cartItemDTOs = CookieUtils.getCartFromCookies(request);
            cartItemDTOs.add(cartItemDTO);

            //Merge same product cart items by summing the quantities
            List<CartItemDTO> mergedCartItemDTOs = mergeSameProductCartItemDTOs(cartItemDTOs);

            Settings lifetimeSetting = settingsService.findBySettingName("CART_COOKIE_LIFETIME");
            Integer CART_COOKIE_LIFETIME_SETTING = null;

            if(lifetimeSetting != null){
                CART_COOKIE_LIFETIME_SETTING = lifetimeSetting.getSettingValueInt();
            }


            CookieUtils.saveCartToCookies(mergedCartItemDTOs, CART_COOKIE_LIFETIME_SETTING, response);
        }
    }

    /*
        Merge same product cookie cart items
     */
    private List<CartItemDTO> mergeSameProductCartItemDTOs(List<CartItemDTO> cartItemDTOs) {
        // Map to store merged CartItemDTOs by productId
        Map<Integer, CartItemDTO> mergedCartItems = new HashMap<>();

        for (CartItemDTO cartItemDTO : cartItemDTOs) {
            Integer productId = cartItemDTO.getProductId();
            // If the productId is already in the map, sum the quantity
            if (mergedCartItems.containsKey(productId)) {
                CartItemDTO existingItem = mergedCartItems.get(productId);
                existingItem.setQuantity(existingItem.getQuantity() + cartItemDTO.getQuantity());
            } else {
                // If not, add the new CartItemDTO
                mergedCartItems.put(productId, cartItemDTO);
            }
        }

        // Return the list of merged CartItemDTOs
        return new ArrayList<>(mergedCartItems.values());

    }


    public void mergeCart(HttpServletRequest request, HttpServletResponse response) {
        CustomUserDetails userDetails = userService.getCurrentUser();
        if (userDetails != null) {

            // Retrieve cart items from cookies
            List<CartItem> cookieCart = new ArrayList<>();

            for(CartItemDTO dto: CookieUtils.getCartFromCookies(request)){
                CartItem cartItem = cartItemService.mapToCartItem(dto);
                cookieCart.add(cartItem);
            }

            if(!cookieCart.isEmpty()){
                // Fetch or create the database cart
                Cart cart = cartRepository.findByUser(userDetails.getUser()).orElse(null);
                if (cart == null) {
                    cart = new Cart();
                    cart.setUser(userDetails.getUser()); // Assuming a User entity
                    cart = cartRepository.save(cart);
                }

                // Merge cookie items into the database
                for (CartItem item : cookieCart) {
                    item.setCart(cart);
                    CartItem savedItem = cartItemRepository.findByCartAndProduct(cart, item.getProduct());
                    if (savedItem != null) {
                        savedItem.setQuantity(savedItem.getQuantity() + item.getQuantity());
                    }else {
                        savedItem = item;
                    }

                    cartItemRepository.save(savedItem);

                }

                // Clear the cart cookies
                CookieUtils.clearCartCookies(response);
            }
        }
    }

    public Cart findCartById(Integer cartId) {
        return cartRepository.findById(cartId).orElse(null);
    }

    public void deleteByUser() {
        CustomUserDetails userDetails = userService.getCurrentUser();
        if(userDetails != null){

            cartRepository.deleteByUser(userDetails.getUser());
        }

    }

    public void clearCart(HttpServletResponse response) {
        // Clear cookie cart
        CookieUtils.clearCartCookies(response);

        // Delete DB cart
        CustomUserDetails userDetails = userService.getCurrentUser();
        if(userDetails != null){
            cartRepository.deleteByUser(userDetails.getUser());
        }

    }
}
