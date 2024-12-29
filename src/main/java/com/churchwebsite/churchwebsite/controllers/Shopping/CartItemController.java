package com.churchwebsite.churchwebsite.controllers.Shopping;

import com.churchwebsite.churchwebsite.entities.shopping.CartItem;
import com.churchwebsite.churchwebsite.services.shopping.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/shopping/cart/item")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @GetMapping
    public String getAllCartItems(Model model) {
        List<CartItem> cartItems = cartItemService.getAllCartItems();
        model.addAttribute("cartItems", cartItems);
        return "cart-item/list";
    }

    @GetMapping("/{id}")
    public String getCartItemById(@PathVariable Integer id, Model model) {
        model.addAttribute("cartItem", cartItemService.getCartItemById(id).orElse(null));
        return "cart-item/detail";
    }

    @PostMapping
    public String createCartItem(@ModelAttribute CartItem cartItem) {
        cartItemService.saveCartItem(cartItem);
        return "redirect:/cart-items";
    }

    @PutMapping("/{id}")
    public String updateCartItem(@PathVariable Integer id, @ModelAttribute CartItem updatedCartItem) {
        cartItemService.updateCartItem(id, updatedCartItem);
        return "redirect:/cart-items";
    }

    @DeleteMapping("/{id}")
    public String deleteCartItem(@PathVariable Integer id) {
        cartItemService.deleteCartItem(id);
        return "redirect:/cart-items";
    }

//    @GetMapping("/{id}/increment")
//    public String incrementCartItemQuantity(@PathVariable("id") Integer cartItemId,
//                                            Model model,
//                                            HttpServletRequest request,
//                                            HttpServletResponse response){
//
//        CartItem cartItem = cartItemService.getCartItemById(cartItemId).orElse(null);
//
//        if(cartItem != null){
//
//        }
//
//        List<CartItem> cartItems = cartService.getCart(request, response);
//
//        model.addAttribute("activeContentPage", "shopping-cart-detail");
//        model.addAttribute("cartItems", cartItems);
//
//        return PUBLIC_CONTENT;
//    }
}
