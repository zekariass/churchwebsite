package com.churchwebsite.churchwebsite.controllers.Shopping;

import com.churchwebsite.churchwebsite.enums.DeliveryType;
import com.churchwebsite.churchwebsite.services.shopping.CartItemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shopping/cart/items")
public class CartItemController {

    private final CartItemService cartItemService;

    private String PUBLIC_CONTENT = "layouts/base";

    @Autowired
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

//    @GetMapping
//    public String getAllCartItems(Model model) {
//        List<CartItem> cartItems = cartItemService.getAllCartItems();
//        model.addAttribute("cartItems", cartItems);
//        return "cart-item/list";
//    }

//    @GetMapping("/{id}")
//    public String getCartItemById(@PathVariable Integer id, Model model) {
//        model.addAttribute("cartItem", cartItemService.getCartItemById(id).orElse(null));
//        return "cart-item/detail";
//    }
//
//    @PostMapping
//    public String createCartItem(@ModelAttribute CartItem cartItem) {
//        cartItemService.saveCartItem(cartItem);
//        return "redirect:/cart-items";
//    }

//    @PutMapping("/{id}")
//    public String updateCartItem(@PathVariable Integer id, @ModelAttribute CartItem updatedCartItem) {
//        cartItemService.updateCartItem(id, updatedCartItem);
//        return "redirect:/cart-items";
//    }

//    @DeleteMapping("/{id}")
//    public String deleteCartItem(@PathVariable Integer id) {
//        cartItemService.deleteCartItem(id);
//        return "redirect:/cart-items";
//    }

    @PostMapping("/{cartItemId}/increment")
    public String incrementCartItemQuantity(@PathVariable("cartItemId") Integer cartItemId,
                                            @RequestParam("productId") Integer productId,
                                            Model model,
                                            HttpServletRequest request,
                                            HttpServletResponse response){

        cartItemService.incrementQuantity(cartItemId, productId, request, response);

        return "redirect:/shopping/cart/detail";
    }

    @PostMapping("/{cartItemId}/decrement")
    public String decrementCartItemQuantity(@PathVariable("cartItemId") Integer cartItemId,
                                            @RequestParam("productId") Integer productId,
                                            Model model,
                                            HttpServletRequest request,
                                            HttpServletResponse response){

        cartItemService.decrementQuantity(cartItemId, productId, request, response);
        return "redirect:/shopping/cart/detail";
    }


    @GetMapping("/{cartItemId}/delete")
    public String deleteCartItemQuantity(@PathVariable("cartItemId") Integer cartItemId,
                                         @RequestParam("productId") Integer productId,
                                            Model model,
                                            HttpServletRequest request,
                                            HttpServletResponse response){

        cartItemService.deleteCartItem(cartItemId, productId, request, response);
        return "redirect:/shopping/cart/detail";
    }


    @PostMapping("/{cartItemId}/update-delivery")
    public String updateDeliveryTypeForCartItem(@PathVariable("cartItemId") Integer cartItemId,
                                                @RequestParam("deliveryType") DeliveryType deliveryType,
                                                Model model){

        cartItemService.updateCartItemDeliveryType(cartItemId, deliveryType);
        return "redirect:/shopping/cart/detail";
    }

}
