package com.churchwebsite.churchwebsite.controllers.Shopping;

import com.churchwebsite.churchwebsite.entities.shopping.Shipping;
import com.churchwebsite.churchwebsite.enums.ShippingStatus;
import com.churchwebsite.churchwebsite.services.PaginationService;
import com.churchwebsite.churchwebsite.services.shopping.ShippingService;
import com.churchwebsite.churchwebsite.utils.LocaleUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dashboard/orders/shippings")
public class ShippingController {

    private final ShippingService shippingService;
    private final PaginationService paginationService;
    private final LocaleUtil localeUtil;

    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";

    @Autowired
    public ShippingController(ShippingService shippingService, PaginationService paginationService, LocaleUtil localeUtil) {
        this.shippingService = shippingService;
        this.paginationService = paginationService;
        this.localeUtil = localeUtil;
    }

    @GetMapping("")
    public String getAllShipments(Model model,
                   @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                   @RequestParam(value = "size", required = false) Integer pageSize,
                   @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                   HttpServletRequest request){

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<Shipping> pagedShipments = shippingService.getAllShipments(page, pageSize, sortBy);
        List<Shipping> shipments = pagedShipments.getContent();

        model.addAttribute("activeDashPage", "shippings-list");
        model.addAttribute("shippings", shipments);
        model.addAttribute("shippingStatus", ShippingStatus.values());
        model.addAttribute("currencySymbol", localeUtil.getCurrency().getSymbol());

        model.addAttribute("currentPage", pagedShipments.getNumber()+1);
        model.addAttribute("totalItems", pagedShipments.getTotalElements());
        model.addAttribute("totalPages", pagedShipments.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("sortBy", sortBy);

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/detail/{id}")
    public String getShippingById(@PathVariable Integer id, Model model) {
        model.addAttribute("activeDashPage", "shipping-detail");
        model.addAttribute("shippingStatuses", ShippingStatus.values());
        model.addAttribute("shipping", shippingService.getShipmentById(id).orElse(null));
        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/edit/form/{id}")
    public String showShippingForm(Model model, @PathVariable("id") int shippingId, @ModelAttribute Shipping updatedShipping) {
        model.addAttribute("shipping", shippingService.getShipmentById(shippingId));
        model.addAttribute("activeDashPage", "shipping-edit-form");
        model.addAttribute("shippingStatuses", ShippingStatus.values());
        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/edit/form/process")
    public String updateShipping(Model model, @ModelAttribute Shipping updatedShipping) {
        Shipping shipping = shippingService.updateShipment(updatedShipping);
        return "redirect:/dashboard/orders/shippings/detail/"+shipping.getShippingId();
    }

}
