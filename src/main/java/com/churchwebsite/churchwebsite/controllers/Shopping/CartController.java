package com.churchwebsite.churchwebsite.controllers.Shopping;

import com.churchwebsite.churchwebsite.dtos.CheckoutDTO;
import com.churchwebsite.churchwebsite.dtos.ChurchDetailDTO;
import com.churchwebsite.churchwebsite.dtos.ProductRequest;
import com.churchwebsite.churchwebsite.dtos.StripeResponse;
import com.churchwebsite.churchwebsite.entities.Address;
import com.churchwebsite.churchwebsite.entities.Church;
import com.churchwebsite.churchwebsite.entities.shopping.Cart;
import com.churchwebsite.churchwebsite.entities.shopping.CartItem;
import com.churchwebsite.churchwebsite.entities.shopping.Product;
import com.churchwebsite.churchwebsite.entities.shopping.ShippingPlan;
import com.churchwebsite.churchwebsite.enums.DeliveryType;
import com.churchwebsite.churchwebsite.enums.ProductDeliveryType;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.SettingsService;
import com.churchwebsite.churchwebsite.services.UserService;
import com.churchwebsite.churchwebsite.services.payment.StripeService;
import com.churchwebsite.churchwebsite.services.shopping.CartService;
import com.churchwebsite.churchwebsite.services.shopping.ProductService;
import com.churchwebsite.churchwebsite.services.shopping.ShippingPlanService;
import com.churchwebsite.churchwebsite.utils.CustomUserDetails;
import com.churchwebsite.churchwebsite.utils.LocaleUtil;
import com.churchwebsite.churchwebsite.utils.ShoppingUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/shopping/cart")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;
    private final LocaleUtil localeUtil;
    private final SettingsService settingsService;
    private final ShippingPlanService shippingPlanService;
    private  final ChurchDetailService churchDetailService;
    private final StripeService stripeService;
    private final UserService userService;

    private String PUBLIC_CONTENT = "layouts/base";

    @Autowired
    public CartController(CartService cartService, ProductService productService, LocaleUtil localeUtil, SettingsService settingsService, ShippingPlanService shippingPlanService, ChurchDetailService churchDetailService, StripeService stripeService, UserService userService) {
        this.cartService = cartService;
        this.productService = productService;
        this.localeUtil = localeUtil;
        this.settingsService = settingsService;
        this.shippingPlanService = shippingPlanService;
        this.churchDetailService = churchDetailService;
        this.stripeService = stripeService;
        this.userService = userService;
    }

    @GetMapping
    public String getCart(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<CartItem> cartItems = cartService.getCart(request, response);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("currencySymbol", localeUtil.getCurrency().getSymbol());
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

            // Default collection type
            cartItem.setDeliveryType(DeliveryType.COLLECTION);

            System.out.println("=================================>>>: "+ cartItem);

            cartService.addToCart(request, response, cartItem);
        }

        String referer = request.getHeader("Referer");
        if(referer != null && !referer.isEmpty()){
            return "redirect:"+referer;
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
        model.addAttribute("currencySymbol", localeUtil.getCurrency().getSymbol());

        model.addAttribute("activeContentPage", "shopping-cart-detail");
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("currencySymbol", localeUtil.getCurrency().getSymbol());

        double taxRateFixed = settingsService.findBySettingName("TAX_RATE_FIXED").getSettingValueDouble();
        double taxRatePercent = settingsService.findBySettingName("TAX_RATE_PERCENT").getSettingValueDouble();
        double cartSubTotal = ShoppingUtils.getCartSubtotal(cartItems);

        model.addAttribute("totalTax", ( cartSubTotal * taxRatePercent)/100 + taxRateFixed);
        model.addAttribute("cartSubTotal", cartSubTotal);
        model.addAttribute("cartTotalPrice", cartSubTotal +( cartSubTotal * taxRatePercent)/100 + taxRateFixed);
        model.addAttribute("deliveryTypes", DeliveryType.values());
        model.addAttribute("deliveryOrCollect", ProductDeliveryType.DELIVERY_OR_COLLECT);
        model.addAttribute("checkoutDto", new CheckoutDTO());
        model.addAttribute("shippingPrice", 0.0);

        return PUBLIC_CONTENT;
    }

    @GetMapping("/checkout")
    public String shoppingCheckoutPage(@ModelAttribute CheckoutDTO checkoutDTO,
                                       Model model,
                                       HttpServletRequest request,
                                       HttpServletResponse response){

        ChurchDetailDTO churchDetailDTO = churchDetailService.getChurchDetail();
        List<ShippingPlan> shippingPlans = shippingPlanService.findAll();
        List<CartItem> cartItems = cartService.getCart(request, response);
        List<CartItem> shippableCartItems = cartItems.stream().filter(item -> item.getDeliveryType() == DeliveryType.COLLECTION).toList();

        ShippingPlan domesticPlan = shippingPlans.stream().filter(plan -> "DOMESTIC_PLAN".equals(plan.getPlanName())).findFirst().orElse(new ShippingPlan());
        ShippingPlan internationalPlan = shippingPlans.stream().filter(plan -> "INTERNATIONAL_PLAN".equals(plan.getPlanName())).findFirst().orElse(new ShippingPlan());
        double shippingPrice = 0.0;

        if(churchDetailDTO != null && checkoutDTO != null) {
            if (!shippableCartItems.isEmpty()) {
                double totalWeight = ShoppingUtils.getTotalWeightsForCart(shippableCartItems);

                if (Objects.equals(checkoutDTO.getAddress().getCountry().toLowerCase(), churchDetailDTO.getAddress().getCountry().toLowerCase()) ||
                        Objects.equals(checkoutDTO.getAddress().getCity().toLowerCase(), churchDetailDTO.getAddress().getCity().toLowerCase()) ||
                        Objects.equals(checkoutDTO.getAddress().getState().toLowerCase(), churchDetailDTO.getAddress().getState().toLowerCase())) {
                    shippingPrice = ShoppingUtils.computeShippingPrice(0,
                            totalWeight,
                            domesticPlan.getBasePrice(),
                            domesticPlan.getPerMilePrice(),
                            domesticPlan.getPerKgPrice());
                } else {
                    shippingPrice = ShoppingUtils.computeShippingPrice(0,
                            totalWeight,
                            internationalPlan.getBasePrice(),
                            internationalPlan.getPerMilePrice(),
                            internationalPlan.getPerKgPrice());
                }
            }
        }



        double taxRateFixed = settingsService.findBySettingName("TAX_RATE_FIXED").getSettingValueDouble();
        double taxRatePercent = settingsService.findBySettingName("TAX_RATE_PERCENT").getSettingValueDouble();
        double cartSubTotal = ShoppingUtils.getCartSubtotal(cartItems);

        Cart cart = null;
        if(!cartItems.isEmpty()){
            cart = cartItems.getFirst().getCart();
        }

        model.addAttribute("churchDetail", churchDetailDTO);
        model.addAttribute("activeContentPage", "checkout-page");
        model.addAttribute("currencySymbol", localeUtil.getCurrency().getSymbol());
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cart", cart);
        model.addAttribute("currencySymbol", localeUtil.getCurrency().getSymbol());
        model.addAttribute("totalTax", ( cartSubTotal * taxRatePercent)/100 + taxRateFixed);
        model.addAttribute("cartSubTotal", cartSubTotal);
        model.addAttribute("cartTotalPrice", cartSubTotal +( cartSubTotal * taxRatePercent)/100 + taxRateFixed + shippingPrice);
        model.addAttribute("deliveryModes", DeliveryType.values());
        model.addAttribute("shippingPrice", shippingPrice);
        model.addAttribute("confirmationPage", true);
        assert checkoutDTO != null;
        model.addAttribute("fullShippingAddress", checkoutDTO.getAddress().getFullAddress());
        model.addAttribute("shippingAddress", checkoutDTO.getAddress());

        return PUBLIC_CONTENT;
    }

    @PostMapping("/confirmAndPay")
    public RedirectView confirmAndPay(@RequestParam("street") String street,
                                @RequestParam("buildingNo") String buildingNo,
                                @RequestParam("houseNo") String houseNo,
                                @RequestParam("city") String city,
                                @RequestParam("state") String state,
                                @RequestParam("country") String country,
                                @RequestParam("postCode") String postCode,

                                @RequestParam("shippingPrice") double shippingPrice,
                                @RequestParam("cartTotalPrice") double cartTotalPrice,
                                @RequestParam("totalTax") double totalTax,
                                HttpServletRequest request,
                                HttpServletResponse response,
                                Model model){

        CustomUserDetails userDetails = userService.getCurrentUser();
        StripeResponse stripeResponse;
        if(userDetails.getUser() != null) {
            List<CartItem> cartItems = cartService.getCart(request, response);
            Address address = new Address(street, buildingNo, houseNo, city, state, country, postCode);
            long actualStripeAmount = (long) cartTotalPrice * 100;
            Church church = churchDetailService.getChurchDetail().getChurch();
            ProductRequest productRequest = new ProductRequest(actualStripeAmount, 1L, church.getChurchName() + " Shop", localeUtil.getCurrency().getCurrencyCode());

            // Call service to process checkout
            stripeResponse = stripeService.checkoutProducts(productRequest,
                    "http://localhost:9090/shopping/cart/checkout-success",
                    "http://localhost:9090/shopping/cart/checkout-cancel");

            // Add the response to the model
            model.addAttribute("stripeResponse", stripeResponse);

            model.addAttribute("activeContentPage", "checkout-payment-result");
            model.addAttribute("address", address);

            // Return the name of the Thymeleaf template (e.g., "checkoutResult")
            return new RedirectView(stripeResponse.getSessionUrl());
        }

        return null;
    }

    @GetMapping("/checkout-success")
    public String showCheckoutSuccess(Model model){

        model.addAttribute("activeContentPage", "checkout-success");

        return PUBLIC_CONTENT;
    }


    @GetMapping("/checkout-cancel")
    public String showCheckoutCancellation(Model model){

        model.addAttribute("activeContentPage", "checkout-cancel");

        return PUBLIC_CONTENT;
    }
}

