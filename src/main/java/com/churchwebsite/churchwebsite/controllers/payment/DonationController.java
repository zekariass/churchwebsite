package com.churchwebsite.churchwebsite.controllers.payment;

import com.churchwebsite.churchwebsite.dtos.ChurchDetailDTO;
import com.churchwebsite.churchwebsite.dtos.ProductRequest;
import com.churchwebsite.churchwebsite.dtos.StripeResponse;
import com.churchwebsite.churchwebsite.entities.Donation;
import com.churchwebsite.churchwebsite.enums.DonationPaymentMethod;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.NotificationService;
import com.churchwebsite.churchwebsite.services.SettingsService;
import com.churchwebsite.churchwebsite.services.payment.DonationPurposeService;
import com.churchwebsite.churchwebsite.services.payment.DonationService;
import com.churchwebsite.churchwebsite.services.payment.StripeService;
import com.churchwebsite.churchwebsite.utils.LocaleUtil;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes({"donation"})
@RequestMapping("/donation")
public class DonationController {

    private StripeService stripeService;
    private final DonationPurposeService donationPurposeService;
    private final DonationService donationService;
    private final NotificationService notificationService;

    private final String PUBLIC_CONTENT = "layouts/base";
    private final ChurchDetailService churchDetailService;
    private final SettingsService settingsService;
    private final LocaleUtil localeUtil;

    private final Logger logger = LoggerFactory.getLogger(DonationController.class);

    @Autowired
    public DonationController(StripeService stripeService, DonationPurposeService donationPurposeService, DonationService donationService, NotificationService notificationService,
                              ChurchDetailService churchDetailService,
                              SettingsService settingsService, LocaleUtil localeUtil) {
        this.stripeService = stripeService;
        this.donationPurposeService = donationPurposeService;
        this.donationService = donationService;
        this.notificationService = notificationService;
        this.churchDetailService = churchDetailService;
        this.settingsService = settingsService;
        this.localeUtil = localeUtil;
    }

    @GetMapping("/options")
    public String showDonationOptions(Model model){

        setModelAttributesForDonationOptions(model);
        model.addAttribute("donation", new Donation());

        return PUBLIC_CONTENT;
    }

    private void setModelAttributesForDonationOptions(Model model) {
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("productRequest", new ProductRequest());
        model.addAttribute("currencyCode", localeUtil.getCurrency().getSymbol());
        model.addAttribute("currencySymbol", localeUtil.getCurrency().getSymbol());
        model.addAttribute("donationPurposes", donationPurposeService.getAllDonationPurposes());
        model.addAttribute("donationPaymentTypes", DonationPaymentMethod.values());
        model.addAttribute("pageTitle", "Donation Options");
        model.addAttribute("activeContentPage", "donation-options");
    }


    @PostMapping("/options")
    public String donationCheckout(@Valid @ModelAttribute("donation") Donation donation,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {

//        System.out.println("===========================================: "+ donation);

        boolean fieldError = false;
        if((donation.getDirectDebitAccount() != null || donation.getDirectDebitSortCode() != null) &&
                (donation.getDonorFullName() == null || donation.getDonorFullName().isEmpty())){
            model.addAttribute("fullNameError", "You must provide your full name for direct debit");
            fieldError = true;
        }


        if(donation.getPaymentMethod() == DonationPaymentMethod.DIRECT_DEBIT_MONTHLY &&
            (donation.getDirectDebitSortCode() == null || donation.getDirectDebitSortCode().isEmpty())){
            model.addAttribute("sortCodeError", "You must provide sort-code.");
            fieldError = true;
        }

        if(donation.getPaymentMethod() == DonationPaymentMethod.DIRECT_DEBIT_MONTHLY &&
                (donation.getDirectDebitAccount() == null || donation.getDirectDebitAccount().isEmpty())){
            model.addAttribute("accountError", "You must provide account number.");
            fieldError = true;
        }

        // If there is validation error
        if(result.hasErrors() || fieldError){
            setModelAttributesForDonationOptions(model);
            donation.setDonationPurpose(null);
            model.addAttribute("donation", donation);

            return PUBLIC_CONTENT;
        }

        // If no validation error and if it is card payment
        if(donation.getPaymentMethod() == DonationPaymentMethod.CREDIT_OR_DEBIT_CARD){
            long actualDonationAmount = Math.round(donation.getDonationAmount() * 100.0);
            ProductRequest productRequest = new ProductRequest(actualDonationAmount, 1L, "Church Donation", localeUtil.getCurrency().getCurrencyCode());

            // Call service to process checkout
            StripeResponse stripeResponse = stripeService.checkoutProducts(productRequest,
                    "http://localhost:9090/donation/donation-success",
                    "http://localhost:9090/donation/donation-cancel");


            // if there is a failure in stripe payment
            if(stripeResponse.getStatus().equals("FAILURE")){
                donation.setDonationPurpose(null);
                setModelAttributesForDonationOptions(model);
                model.addAttribute("stripeResponse", stripeResponse);
                model.addAttribute("donation", donation);

                return PUBLIC_CONTENT;
            }else{
                // Add the response to the model
                redirectAttributes.addFlashAttribute("donation", donation);
                return "redirect:"+ stripeResponse.getSessionUrl();
            }
        }

        // If the donation is other than card payment (it is check or direct debit), save to db
//        donationService.saveDonation(donation);
        return "redirect:/donation/donation-success";

    }


    @GetMapping("/donation-success")
    public String showDonationSuccess(@ModelAttribute("donation") Donation donation,
                                      Model model,
                                      SessionStatus sessionStatus){

        ChurchDetailDTO churchDetailDTO = churchDetailService.getChurchDetail();
        model.addAttribute("activeContentPage", "donation-success");
        model.addAttribute("churchDetail", churchDetailDTO);
        model.addAttribute("pageTitle", "Donation Success");

        try {
            notificationService.sendDonationSuccessEmail(donation, churchDetailDTO.getChurch().getChurchName());
        } catch (MessagingException e) {
            logger.error("=======================>>>>>>: {}", e.getMessage());
        }

        if(donation != null) {
            donationService.saveDonation(donation);
            sessionStatus.setComplete();
        }

        return PUBLIC_CONTENT;
    }


    @GetMapping("/donation-cancel")
    public String showDonationCancellation(@ModelAttribute("donation") Donation donation, Model model){

        ChurchDetailDTO churchDetailDTO = churchDetailService.getChurchDetail();

        model.addAttribute("churchDetail", churchDetailDTO);
        model.addAttribute("activeContentPage", "donation-cancel");
        model.addAttribute("pageTitle", "Donation Cancelled");

        try {
            notificationService.sendDonationFailureEmail(donation, churchDetailDTO.getChurch().getChurchName());
        } catch (MessagingException e) {
            logger.error("========================>>: {}", e.getMessage());
        }

        return PUBLIC_CONTENT;
    }

}
