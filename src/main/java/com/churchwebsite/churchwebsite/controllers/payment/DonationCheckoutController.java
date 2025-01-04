package com.churchwebsite.churchwebsite.controllers.payment;

import com.churchwebsite.churchwebsite.dtos.ProductRequest;
import com.churchwebsite.churchwebsite.dtos.StripeResponse;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.SettingsService;
import com.churchwebsite.churchwebsite.services.payment.StripeService;
import com.churchwebsite.churchwebsite.utils.LocaleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/donation")
public class DonationCheckoutController {

    private StripeService stripeService;
    private final String PUBLIC_CONTENT = "layouts/base";
    private final ChurchDetailService churchDetailService;
    private final SettingsService settingsService;
    private final LocaleUtil localeUtil;

    @Autowired
    public DonationCheckoutController(StripeService stripeService,
                                      ChurchDetailService churchDetailService,
                                      SettingsService settingsService, LocaleUtil localeUtil) {
        this.stripeService = stripeService;
        this.churchDetailService = churchDetailService;
        this.settingsService = settingsService;
        this.localeUtil = localeUtil;
    }

    @GetMapping("/options")
    public String showDonationOptions(Model model){

        model.addAttribute("activeContentPage", "donation-options");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("productRequest", new ProductRequest());
        model.addAttribute("currencyCode", localeUtil.getCurrency().getSymbol());
        model.addAttribute("currencySymbol", localeUtil.getCurrency().getSymbol());

        return PUBLIC_CONTENT;
    }

    @PostMapping("/process")
    public RedirectView checkout(@RequestParam("donationAmount") double donationAmount, Model model) {

        long actualDonationAmount = (long) donationAmount * 100;

        ProductRequest productRequest = new ProductRequest(actualDonationAmount, 1L, "Church Donation", localeUtil.getCurrency().getCurrencyCode());

        // Call service to process checkout
        StripeResponse stripeResponse = stripeService.checkoutProducts(productRequest,
                "http://localhost:9090/donation/donation-success",
                "http://localhost:9090/donation/donation-cancel");

        // Add the response to the model
        model.addAttribute("stripeResponse", stripeResponse);

        model.addAttribute("activeContentPage", "donation-checkout-request-result");

        // Return the name of the Thymeleaf template (e.g., "checkoutResult")

        return new RedirectView(stripeResponse.getSessionUrl());
//        return PUBLIC_CONTENT;  // This will render checkoutResult.html
    }


    @GetMapping("/donation-success")
    public String showDonationSuccess(Model model){

        model.addAttribute("activeContentPage", "donation-success");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());

        return PUBLIC_CONTENT;
    }


    @GetMapping("/donation-cancel")
    public String showDonationCancellation(Model model){

        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("activeContentPage", "donation-cancel");

        return PUBLIC_CONTENT;
    }

}
