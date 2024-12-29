package com.churchwebsite.churchwebsite.controllers.Shopping;

import com.churchwebsite.churchwebsite.entities.shopping.CartItem;
import com.churchwebsite.churchwebsite.entities.shopping.Product;
import com.churchwebsite.churchwebsite.services.shopping.CartService;
import com.churchwebsite.churchwebsite.services.shopping.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/shopping/cart")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    private String PUBLIC_CONTENT = "layouts/base";

    @Autowired
    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping
    public String getCart(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<CartItem> cartItems = cartService.getCart(request, response);
        model.addAttribute("cartItems", cartItems);
        return "cart"; // Returns a view named "cart.html"
    }

    @PostMapping("/add")
    public String addToCart(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("quantity") int quantity,
            @RequestParam("productId") int productId) {


        CartItem cartItem = new CartItem();
        Product product = productService.getProductById(productId).orElse(null);

        if(product != null){
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartService.addToCart(request, response, cartItem);
        }
        return "redirect:/shopping/products";
    }

//    @PostMapping("/merge")
//    public String mergeCart(HttpServletRequest request, HttpServletResponse response) {
//        cartService.mergeCart(request, response);
//        return "redirect:/cart";
//    }

    @GetMapping("/delete")
    public String deleteCart(HttpServletResponse response){
        cartService.clearCart(response);

        return "redirect:/shopping/products";
    }


    @GetMapping("/delete-by-user")
    public String deleteCartByUser(){

        cartService.deleteByUser();

        return "redirect:/shopping/products";
    }


    @GetMapping("/detail")
    public String showCartDetail(Model model, HttpServletRequest request, HttpServletResponse response){

        List<CartItem> cartItems = cartService.getCart(request, response);

        model.addAttribute("activeContentPage", "shopping-cart-detail");
        model.addAttribute("cartItems", cartItems);

        return PUBLIC_CONTENT;
    }
}

