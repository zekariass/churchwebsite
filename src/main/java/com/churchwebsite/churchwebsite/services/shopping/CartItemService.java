package com.churchwebsite.churchwebsite.services.shopping;

import com.churchwebsite.churchwebsite.dtos.CartItemDTO;
import com.churchwebsite.churchwebsite.entities.shopping.Cart;
import com.churchwebsite.churchwebsite.entities.shopping.CartItem;
import com.churchwebsite.churchwebsite.entities.shopping.Product;
import com.churchwebsite.churchwebsite.repositories.Shopping.CartItemRepository;
import com.churchwebsite.churchwebsite.repositories.Shopping.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductService productService;

    public CartItemService(CartItemRepository cartItemRepository, CartRepository cartRepository, ProductService productService) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productService = productService;
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

    public void deleteCartItem(Integer id) {
        cartItemRepository.deleteById(id);
    }

    public CartItemDTO mapToDTO(CartItem cartItem){

        CartItemDTO cartItemDTO = new CartItemDTO();

        cartItemDTO.setCartItemId(cartItem.getCartItemId());
        cartItemDTO.setCartId(cartItem.getCart() != null? cartItem.getCart().getCartId(): 0);
        cartItemDTO.setProductId(cartItem.getProduct().getProductId());
        cartItemDTO.setQuantity(cartItem.getQuantity());

        return cartItemDTO;
    }

    public CartItem mapToCartItem(CartItemDTO dto){
        CartItem cartItem = new CartItem();

        cartItem.setCartItemId(dto.getCartId());
        cartItem.setCart(cartRepository.findById(dto.getCartId()).orElse(null));
        cartItem.setProduct(productService.getProductById(dto.getProductId()).orElse(null));
        cartItem.setQuantity(dto.getQuantity());

        return cartItem;
    }

    public CartItem findByCart(Cart cart) {
        return cartItemRepository.findByCart(cart);
    }

    public CartItem findByCartAndProduct(Cart cart, Product product) {
        return cartItemRepository.findByCartAndProduct(cart, product);
    }
}

