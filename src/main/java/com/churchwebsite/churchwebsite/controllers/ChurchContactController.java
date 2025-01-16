package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.ChurchContact;
import com.churchwebsite.churchwebsite.services.ChurchContactService;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("dashboard/church-contacts")
public class ChurchContactController {

    private final ChurchContactService contactService;
    private final ChurchDetailService churchDetailService;

//    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";
    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-layouts/dash-base";

    @Autowired
    public ChurchContactController(ChurchContactService contactService, ChurchDetailService churchDetailService) {
        this.contactService = contactService;
        this.churchDetailService = churchDetailService;
    }

    // Display all contacts
    @GetMapping("")
    public String listContacts(Model model) {
        List<ChurchContact> contacts = contactService.findAllContacts();
        model.addAttribute("contacts", contacts);
        model.addAttribute("activeDashPage", "church-contacts-list");

        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Church Contact List");

        return DASHBOARD_MAIN_PANEL;
    }

    // Show form to create a new contact
    @GetMapping("/form")
    public String showCreateForm(Model model) {
        model.addAttribute("contact", new ChurchContact());
        model.addAttribute("activeDashPage", "church-contact-form");

        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Church Contact Form");

        return DASHBOARD_MAIN_PANEL;
    }

    // Handle form submission for creating a new contact
    @PostMapping("/form/process")
    public String createContact(@ModelAttribute("contact") ChurchContact contact, BindingResult result, Model model) {
        if (result.hasErrors()) {

            model.addAttribute("activeDashPage", "church-contact-form");
            model.addAttribute("pageTitle", "Church Contact Form");

            return DASHBOARD_MAIN_PANEL;
        }

        contactService.saveContact(contact);
        return "redirect:/dashboard/church-contacts";
    }

    // Show form to edit an existing contact
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        ChurchContact contact = contactService.findContactById(id);
        model.addAttribute("contact", contact);

        model.addAttribute("activeDashPage", "church-contact-form");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Church Contact Form");

        return DASHBOARD_MAIN_PANEL;
    }

    // Show form to edit an existing contact
    @GetMapping("/detail/{id}")
    public String showContactDetail(@PathVariable int id, Model model) {
        ChurchContact contact = contactService.findContactById(id);
        model.addAttribute("contact", contact);

        model.addAttribute("activeDashPage", "church-contact-detail");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Church Contact Detail");

        return DASHBOARD_MAIN_PANEL;
    }

    // Handle form submission for updating an existing contact
    @PostMapping("/update/{id}")
    public String updateContact(@PathVariable int id,
                                @ModelAttribute("contact") ChurchContact contact,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {

            model.addAttribute("activeDashPage", "church-contact-form");
            model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
            model.addAttribute("pageTitle", "Church Contact Form");


            return DASHBOARD_MAIN_PANEL;
        }

        contactService.updateContact(id, contact);
        return "redirect:/dashboard/church-contacts";
    }

    // Delete a contact
    @GetMapping("/delete/{id}")
    public String deleteContact(@PathVariable int id) {
        contactService.deleteContact(id);
        return "redirect:/dashboard/church-contacts";
    }
}
