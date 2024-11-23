package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.Address;
import com.churchwebsite.churchwebsite.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/update/{id}")
    public String showAddressUpdateForm(@PathVariable("id") int addressId, Model model){

        Optional<Address> address = addressService.findById(addressId);

        if(address.isPresent()){
            model.addAttribute("address", address);
        }else {
            model.addAttribute("address", new Address());
        }

        model.addAttribute("activeDashPage", "updateAddress");

        return "dashboard/dash-fragments/dash-main-panel";
    }

    @PostMapping("/processAddressUpdate")
    public String processAddressUpdate(@RequestHeader(value = "Referer", required = false) final String referer,
                                       @RequestParam(value = "returnUrl", required = false) String returnUrl,
                                       @ModelAttribute Address address){
        addressService.save(address);

        System.out.println("=============================>>>>: "+ returnUrl);

        return "redirect:"+(returnUrl != null ? returnUrl : "/dashboard/dash-fragments/dash-main-panel");
    }
}
