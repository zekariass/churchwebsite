package com.churchwebsite.churchwebsite.controllers.Shopping;

import com.churchwebsite.churchwebsite.dtos.CheckoutDTO;
import com.churchwebsite.churchwebsite.dtos.ChurchDetailDTO;
import com.churchwebsite.churchwebsite.dtos.ProductRequest;
import com.churchwebsite.churchwebsite.dtos.StripeResponse;
import com.churchwebsite.churchwebsite.entities.Address;
import com.churchwebsite.churchwebsite.entities.Church;
import com.churchwebsite.churchwebsite.entities.shopping.*;
import com.churchwebsite.churchwebsite.enums.*;
import com.churchwebsite.churchwebsite.services.AddressService;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.SettingsService;
import com.churchwebsite.churchwebsite.services.UserService;
import com.churchwebsite.churchwebsite.services.payment.StripeService;
import com.churchwebsite.churchwebsite.services.shopping.*;
import com.churchwebsite.churchwebsite.utils.CustomUserDetails;
import com.churchwebsite.churchwebsite.utils.LocaleUtil;
import com.churchwebsite.churchwebsite.utils.ShoppingUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/shopping/cart")
@SessionAttributes({"checkoutDTO", "stripeResponse"})
public class CartController {

    private final CartService cartService;
    private final ProductService productService;
    private final LocaleUtil localeUtil;
    private final SettingsService settingsService;
    private final ShippingPlanService shippingPlanService;
    private  final ChurchDetailService churchDetailService;
    private final StripeService stripeService;
    private final UserService userService;
    private final AddressService addressService;
    private final OrdersService ordersService;
    private final OrderItemService orderItemService;
    private final ShippingService shippingService;
    private final OrderPaymentService orderPaymentService;


    private String PUBLIC_CONTENT = "layouts/base";

    @Autowired
    public CartController(CartService cartService, ProductService productService, LocaleUtil localeUtil, SettingsService settingsService, ShippingPlanService shippingPlanService, ChurchDetailService churchDetailService, StripeService stripeService, UserService userService, AddressService addressService, OrdersService ordersService, OrderItemService orderItemService, ShippingService shippingService, OrderPaymentService orderPaymentService) {
        this.cartService = cartService;
        this.productService = productService;
        this.localeUtil = localeUtil;
        this.settingsService = settingsService;
        this.shippingPlanService = shippingPlanService;
        this.churchDetailService = churchDetailService;
        this.stripeService = stripeService;
        this.userService = userService;
        this.addressService = addressService;
        this.ordersService = ordersService;
        this.orderItemService = orderItemService;
        this.shippingService = shippingService;
        this.orderPaymentService = orderPaymentService;
    }


    @GetMapping
    public String getCart(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<CartItem> cartItems = cartService.getCart(request, response);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("currencySymbol", localeUtil.getCurrency().getSymbol());
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
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
            cartService.addToCart(request, response, cartItem);
        }

        String referer = request.getHeader("Referer");
        if(referer != null && !referer.isEmpty()){
            return "redirect:"+referer;
        }
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
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());


        return PUBLIC_CONTENT;
    }

    @GetMapping("/checkout")
    public String shoppingCheckoutPage(@ModelAttribute("checkoutDTO") CheckoutDTO checkoutDTO,
                                       Model model,
                                       HttpServletRequest request,
                                       HttpServletResponse response){

        // Get church detail
        ChurchDetailDTO churchDetailDTO = churchDetailService.getChurchDetail();

        // Get shipping plans
        List<ShippingPlan> shippingPlans = shippingPlanService.findAll();

        // Get the current cart content
        List<CartItem> cartItems = cartService.getCart(request, response);

        // Filter cart items that are to be delivered (not collectable)
        List<CartItem> shippableCartItems = cartItems.stream().filter(item -> item.getDeliveryType() == DeliveryType.COLLECTION).toList();

        double shippingPrice = 0.0;

        if(churchDetailDTO != null && checkoutDTO != null && !shippableCartItems.isEmpty()) {
            shippingPrice = computeShippingPrice(checkoutDTO, shippableCartItems, churchDetailDTO, shippingPlans);
        }

        // Get tax rates if there is any
        double taxRateFixed = settingsService.findBySettingName("TAX_RATE_FIXED").getSettingValueDouble();
        double taxRatePercent = settingsService.findBySettingName("TAX_RATE_PERCENT").getSettingValueDouble();

        // Calculate cart total price before shipping price and  tax added
        double cartSubTotal = ShoppingUtils.getCartSubtotal(cartItems);

        // Get cart from the first cart item
        Cart cart = null;
        if(!cartItems.isEmpty()){
            cart = cartItems.getFirst().getCart();
        }

        // Calculate tax to be applied
        double totalTax = ( cartSubTotal * taxRatePercent)/100 + taxRateFixed;

        // Compute total price including tax and shipping price
        double cartTotalPrice = cartSubTotal + totalTax + shippingPrice;

        // Set new checkout object of it was null and set its attributes
        if(checkoutDTO == null){
            checkoutDTO = new CheckoutDTO();
        }

        checkoutDTO.setTotalPrice(cartTotalPrice);
        assert cart != null;
        checkoutDTO.setCartId(cart.getCartId());
        checkoutDTO.setShippingPrice(shippingPrice);
        checkoutDTO.setTotalTax(totalTax);

        // Set model attributes
        model.addAttribute("churchDetail", churchDetailDTO);
        model.addAttribute("activeContentPage", "checkout-page");
        model.addAttribute("currencySymbol", localeUtil.getCurrency().getSymbol());
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cart", cart);
        model.addAttribute("currencySymbol", localeUtil.getCurrency().getSymbol());
        model.addAttribute("deliveryModes", DeliveryType.values());
        model.addAttribute("shippingPrice", shippingPrice);
        model.addAttribute("fullShippingAddress", checkoutDTO.getAddress().getFullAddress());
        model.addAttribute("totalTax", totalTax);
        model.addAttribute("cartSubTotal", cartSubTotal);
        model.addAttribute("cartTotalPrice", cartTotalPrice);
        model.addAttribute("shippingAddress", checkoutDTO.getAddress());
        model.addAttribute("confirmationPage", true);
        model.addAttribute("checkoutDTO", checkoutDTO);


        return PUBLIC_CONTENT;
    }

    private double computeShippingPrice(CheckoutDTO checkoutDTO, List<CartItem> shippableCartItems, ChurchDetailDTO churchDetailDTO, List<ShippingPlan> shippingPlans) {
        double shippingPrice;
        double totalWeight = ShoppingUtils.getTotalWeightsForCart(shippableCartItems);

        if (Objects.equals(checkoutDTO.getAddress().getCountry().toLowerCase(), churchDetailDTO.getAddress().getCountry().toLowerCase()) ||
                Objects.equals(checkoutDTO.getAddress().getCity().toLowerCase(), churchDetailDTO.getAddress().getCity().toLowerCase()) ||
                Objects.equals(checkoutDTO.getAddress().getState().toLowerCase(), churchDetailDTO.getAddress().getState().toLowerCase())) {

            // Calculate domestic shipping plan
            ShippingPlan domesticPlan = shippingPlans.stream().filter(plan -> "DOMESTIC_PLAN".equals(plan.getPlanName())).findFirst().orElse(new ShippingPlan());

            shippingPrice = ShoppingUtils.computeShippingPrice(0,
                    totalWeight,
                    domesticPlan.getBasePrice(),
                    domesticPlan.getPerMilePrice(),
                    domesticPlan.getPerKgPrice());
        } else {

            // Calculate international shipping plan
            ShippingPlan internationalPlan = shippingPlans.stream().filter(plan -> "INTERNATIONAL_PLAN".equals(plan.getPlanName())).findFirst().orElse(new ShippingPlan());

            shippingPrice = ShoppingUtils.computeShippingPrice(0,
                    totalWeight,
                    internationalPlan.getBasePrice(),
                    internationalPlan.getPerMilePrice(),
                    internationalPlan.getPerKgPrice());
        }
        return shippingPrice;
    }

    @PostMapping("/confirmAndPay")
    public RedirectView confirmAndPay(@RequestParam("cartTotalPrice") double cartTotalPrice,
                                        HttpServletRequest request,
                                        HttpServletResponse response,
                                        Model model){

        CustomUserDetails userDetails = userService.getCurrentUser();
        StripeResponse stripeResponse;

        // Check if the user is already logged in
        if(userDetails.getUser() != null) {
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

            // Return the name of the Thymeleaf template (e.g., "checkoutResult")
            return new RedirectView(stripeResponse.getSessionUrl());
        }

        return null;
    }


    @GetMapping("/checkout-success")
    public String showCheckoutSuccess(Model model,
                                      ModelMap modelMap,
                                      @ModelAttribute("checkoutDTO") CheckoutDTO checkoutDTO,
                                      @ModelAttribute("stripeResponse") StripeResponse stripeResponse){
        //SAVE ORDER INFORMATION

        if(checkoutDTO != null && stripeResponse.getSessionUrl() != null) {
            // Save Shipping Address
            Address savedAddress = addressService.save(checkoutDTO.getAddress());
            Cart cart = cartService.findCartById(checkoutDTO.getCartId());
            List<CartItem> cartItems = cart.getItems();

            // Calculate total number of items in the order
            int totalItemsInOrder = cartItems.stream().mapToInt(CartItem::getQuantity).sum();

            // Create and save Shipping
            Shipping savedShipping = saveShippingData(savedAddress);

            // Create and Save Order
            Orders savedOrder = saveOrder(checkoutDTO, cart, totalItemsInOrder, savedShipping);

            // Save Order Items to db
            saveOrderItems(cartItems, savedOrder);

            // Create and save Payment
            saveOrderPayment(checkoutDTO, stripeResponse, savedOrder, cart);

            //Update the products quantity in stock for the products in the order
            updateProductStockQuantity(cartItems);

            // Delete the cart after order is saved successfully
            cartService.deleteById(cart.getCartId());
        }

        // Delete the checkout session attributes set in this controller
        modelMap.remove("checkoutDTO");
        modelMap.remove("stripeResponse");

        model.addAttribute("activeContentPage", "checkout-success");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());

        return PUBLIC_CONTENT;
    }

    private void saveOrderPayment(CheckoutDTO checkoutDTO, StripeResponse stripeResponse, Orders savedOrder, Cart cart) {
        OrderPayment orderPayment = new OrderPayment();
        orderPayment.setOrder(savedOrder);
        orderPayment.setUser(cart.getUser());
        orderPayment.setAmount(checkoutDTO.getTotalPrice());
        orderPayment.setPaymentMethod("CREDIT_CARD");
        orderPayment.setStatus(PaymentStatus.SUCCESS);
        orderPayment.setSessionId(stripeResponse.getSessionId());
        orderPayment.setSessionUrl(stripeResponse.getSessionUrl());
        orderPaymentService.savePayment(orderPayment);
    }

    private Shipping saveShippingData(Address savedAddress) {
        Shipping shipping = new Shipping();
        shipping.setAddress(savedAddress);
        shipping.setStatus(ShippingStatus.PENDING);
        shipping.setShippedAt(null);
        shipping.setDeliveredAt(null);
        return shippingService.saveShipment(shipping);
    }

    private void saveOrderItems(List<CartItem> cartItems, Orders savedOrder) {
        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem cartItem: cartItems){
            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(savedOrder);
            orderItem.setDeliveryType(cartItem.getDeliveryType());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItem.setTotalPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
            orderItem.setStatus(OrderStatus.SUBMITTED);

            orderItems.add(orderItem);
        }
        orderItemService.saveOrderItems(orderItems);
    }

    private Orders saveOrder(CheckoutDTO checkoutDTO, Cart cart, int totalItemsInOrder, Shipping savedShipping) {
        Orders order = new Orders();
        order.setUser(cart.getUser());
        order.setTotalPrice(checkoutDTO.getTotalPrice());
        order.setTotalQuantity(totalItemsInOrder);
        order.setTax(checkoutDTO.getTotalTax());
        order.setStatus(OrderStatus.SUBMITTED);
        order.setShippingPrice(checkoutDTO.getShippingPrice());
        order.setShipping(savedShipping);
        return ordersService.saveOrder(order);
    }

    private void updateProductStockQuantity(List<CartItem> cartItems) {
        for(CartItem cartItem: cartItems){
            Product product = cartItem.getProduct();
            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
            productService.saveProduct(product);
        }
    }


    @GetMapping("/checkout-cancel")
    public String showCheckoutCancellation(Model model){
        model.addAttribute("activeContentPage", "checkout-cancel");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        return PUBLIC_CONTENT;
    }


    @ModelAttribute("checkoutDTO")
    public CheckoutDTO checkoutDTO() {
        return new CheckoutDTO(); // Provide a default instance if not already in the session
    }


    @ModelAttribute("stripeResponse")
    public StripeResponse stripeResponse() {
        return new StripeResponse(); // Provide a default instance if not already in the session
    }
}

