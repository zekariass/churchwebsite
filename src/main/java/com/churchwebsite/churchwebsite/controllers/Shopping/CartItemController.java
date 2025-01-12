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
