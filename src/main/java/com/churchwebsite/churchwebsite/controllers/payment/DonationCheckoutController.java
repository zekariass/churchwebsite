package com.churchwebsite.churchwebsite.controllers.payment;

import com.churchwebsite.churchwebsite.dtos.ProductRequest;
import com.churchwebsite.churchwebsite.dtos.StripeResponse;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.SettingsService;
import com.churchwebsite.churchwebsite.services.payment.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Currency;
import java.util.Locale;

@Controller
@RequestMapping("/donation")
public class DonationCheckoutController {

    private StripeService stripeService;
    private final String PUBLIC_CONTENT = "layouts/base";
    private final ChurchDetailService churchDetailService;
    private final SettingsService settingsService;

    @Autowired
    public DonationCheckoutController(StripeService stripeService,
                                      ChurchDetailService churchDetailService,
                                      SettingsService settingsService) {
        this.stripeService = stripeService;
        this.churchDetailService = churchDetailService;
        this.settingsService = settingsService;
    }

    @GetMapping("/options")
    public String showDonationOptions(Model model){

        String localLanguageCode = settingsService.findBySettingName("LOCALE_LANGUAGE_CODE").getSettingValueChar();
        String localCountryCode = settingsService.findBySettingName("LOCALE_COUNTRY_CODE").getSettingValueChar();

        localLanguageCode = localLanguageCode != null ? localLanguageCode: "GB";
        localCountryCode = localCountryCode != null ? localCountryCode: "en";

        Locale locale = new Locale(localLanguageCode, localCountryCode);

        Currency currency = Currency.getInstance(locale);

        model.addAttribute("activeContentPage", "donation-options");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("productRequest", new ProductRequest());
        model.addAttribute("currencyCode", currency.getSymbol());
        model.addAttribute("currencySymbol", currency.getSymbol());

        return PUBLIC_CONTENT;
    }

    @PostMapping("/process")
    public RedirectView checkout(@RequestParam("donationAmount") double donationAmount, Model model) {

        String localLanguageCode = settingsService.findBySettingName("LOCALE_LANGUAGE_CODE").getSettingValueChar();
        String localCountryCode = settingsService.findBySettingName("LOCALE_COUNTRY_CODE").getSettingValueChar();

        localLanguageCode = localLanguageCode != null ? localLanguageCode: "GB";
        localCountryCode = localCountryCode != null ? localCountryCode: "en";

        Locale locale = new Locale(localLanguageCode, localCountryCode);

        Currency currency = Currency.getInstance(locale);

        long actualDonationAmount = (long) donationAmount * 100;

        ProductRequest productRequest = new ProductRequest(actualDonationAmount, 1L, "Church Donation", currency.getCurrencyCode());

        // Call service to process checkout
        StripeResponse stripeResponse = stripeService.checkoutProducts(productRequest);

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

        return PUBLIC_CONTENT;
    }


    @GetMapping("/donation-cancel")
    public String showDonationCancellation(Model model){

        model.addAttribute("activeContentPage", "donation-cancel");

        return PUBLIC_CONTENT;
    }

}
