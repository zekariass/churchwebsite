package com.churchwebsite.churchwebsite.controllers.Shopping;


import com.churchwebsite.churchwebsite.entities.shopping.CartItem;
import com.churchwebsite.churchwebsite.entities.shopping.Product;
import com.churchwebsite.churchwebsite.enums.ProductDeliveryType;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.UserService;
import com.churchwebsite.churchwebsite.services.shopping.CartItemService;
import com.churchwebsite.churchwebsite.services.shopping.CartService;
import com.churchwebsite.churchwebsite.services.shopping.ProductCategoryService;
import com.churchwebsite.churchwebsite.services.shopping.ProductService;
import com.churchwebsite.churchwebsite.utils.LocaleUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/shopping")
public class ShoppingController {

    private final ProductService productService;
    private final ProductCategoryService productCategoryService;
    private final ChurchDetailService churchDetailService;
    private final LocaleUtil localeUtil;
    private final UserService userService;

    private final String PUBLIC_CONTENT = "layouts/base";
    private final CartService cartService;
    private final CartItemService cartItemService;

    public ShoppingController(ProductService productService, ProductCategoryService productCategoryService, ChurchDetailService churchDetailService, LocaleUtil localeUtil, UserService userService, CartService cartService, CartItemService cartItemService) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
        this.churchDetailService = churchDetailService;
        this.localeUtil = localeUtil;
        this.userService = userService;
        this.cartService = cartService;
        this.cartItemService = cartItemService;
    }

    /**
     * Displays the shopping list of products.
     */
    @GetMapping("/products")
    public String shoppingList(Model model, HttpServletRequest request, HttpServletResponse response) {

        List<CartItem> cartItems = cartService.getCart(request, response);

        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("productCategories", productCategoryService.getAllCategories());
        model.addAttribute("deliveryTypes", ProductDeliveryType.values());
        model.addAttribute("currencySymbol", localeUtil.getCurrency().getSymbol());
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("activeContentPage", "public-products-list");

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("productCartItemMap", cartItems.stream()
                .collect(Collectors.toMap(
                        cartItem -> cartItem.getProduct().getProductId(), // Key: product.productId
                        cartItem -> cartItem,                             // Value: cartItem
                        (existing, replacement) -> replacement            // In case of duplicates, keep the replacement
                )));
        model.addAttribute("totalCartItems", cartItems.stream().mapToInt(CartItem::getQuantity).sum());

        return PUBLIC_CONTENT; // Corresponds to shopping-list.html
    }



    /**
     * Displays the product details page.
     */
    @GetMapping("/products/detail/{id}")
    public String productDetails(@PathVariable("id") int productId,
                                 HttpServletRequest request,
                                 HttpServletResponse response,
                                 Model model) {

        List<CartItem> cartItems = cartService.getCart(request, response);
        CartItem productCartItem = null;
        for(CartItem item: cartItems){
            if(productId == item.getProduct().getProductId()){
                productCartItem = item;
                break;
            }
        }

        Product product = productService.getProductById(productId).orElse(null);
        if (product == null) {
            return "redirect:/shopping/products"; // Redirect if product not found
        }
        model.addAttribute("product", product);
        model.addAttribute("galleryImages", product.getGalleryImages()); //
        model.addAttribute("thumbNailImages", product.getGalleryImages()); //
        model.addAttribute("currencySymbol", localeUtil.getCurrency().getSymbol());
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("activeContentPage", "public-product-detail");
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalCartItems", cartItems.stream().mapToInt(CartItem::getQuantity).sum());
        model.addAttribute("productCartItem", productCartItem);

        return PUBLIC_CONTENT; // Corresponds to product-detail.html
    }

    /**
     * Adds a product to the cart.
     */
    @GetMapping("/add-to-cart/{id}")
    public String addToCart(@PathVariable("id") int productId) {
        // Logic for adding the product to the cart goes here
        // Example: cartService.addToCart(userId, productId);
        return "redirect:/products/shopping-list"; // Redirect back to the shopping list
    }

}
