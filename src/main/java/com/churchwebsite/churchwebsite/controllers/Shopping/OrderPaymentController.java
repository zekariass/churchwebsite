package com.churchwebsite.churchwebsite.controllers.Shopping;

import com.churchwebsite.churchwebsite.entities.shopping.OrderPayment;
import com.churchwebsite.churchwebsite.enums.PaymentStatus;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.PaginationService;
import com.churchwebsite.churchwebsite.services.shopping.OrderPaymentService;
import com.churchwebsite.churchwebsite.utils.LocaleUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dashboard/orders/payments")
public class OrderPaymentController {

    private final OrderPaymentService orderPaymentService;
    private final PaginationService paginationService;
    private final LocaleUtil localeUtil;
    private  final ChurchDetailService churchDetailService;

    // private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";
    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-layouts/dash-base";

    @Autowired
    public OrderPaymentController(OrderPaymentService orderPaymentService, PaginationService paginationService, LocaleUtil localeUtil, ChurchDetailService churchDetailService) {
        this.orderPaymentService = orderPaymentService;
        this.paginationService = paginationService;
        this.localeUtil = localeUtil;
        this.churchDetailService = churchDetailService;
    }

    @GetMapping("")
    public String getAllPayments(Model model,
                                 @RequestParam(value = "paymentId", required = false) Integer paymentId,
                                 @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                 @RequestParam(value = "size", required = false) Integer pageSize,
                                 @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                                 HttpServletRequest request){

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<OrderPayment> pagedOrderPayments = orderPaymentService.getAllPayments(page, pageSize, sortBy);
        List<OrderPayment> orderPayments = pagedOrderPayments.getContent();

        model.addAttribute("currentPage", pagedOrderPayments.getNumber()+1);
        model.addAttribute("totalItems", pagedOrderPayments.getTotalElements());
        model.addAttribute("totalPages", pagedOrderPayments.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("sortBy", sortBy);

        model.addAttribute("activeDashPage", "order-payments-list");
        model.addAttribute("orderPayments", orderPayments);
        model.addAttribute("currencySymbol", localeUtil.getCurrency().getSymbol());
        model.addAttribute("paymentStatuses", PaymentStatus.values());
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Payments List");

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/detail/{id}")
    public String getPaymentById(@PathVariable Integer id, Model model) {
        model.addAttribute("orderPayment", orderPaymentService.getPaymentById(id).orElse(null));
        model.addAttribute("currencySymbol", localeUtil.getCurrency().getSymbol());
        model.addAttribute("paymentStatuses", PaymentStatus.values());
        model.addAttribute("activeDashPage", "order-payment-detail");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Payment Detail");

        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/edit/form/process")
    public String createPayment(@ModelAttribute OrderPayment orderPayment) {
        orderPaymentService.savePayment(orderPayment);
        return "redirect:/dashboard/orders/payments";
    }

    @GetMapping("/edit/form/{id}")
    public String updatePayment(@PathVariable Integer id, Model model) {
        OrderPayment orderPayment = orderPaymentService.getPaymentById(id).orElse(null);
        model.addAttribute("orderPayment", orderPaymentService.getPaymentById(id).orElse(null));
        model.addAttribute("currencySymbol", localeUtil.getCurrency().getSymbol());
        model.addAttribute("paymentStatuses", PaymentStatus.values());
        model.addAttribute("activeDashPage", "order-payment-edit-form");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Order Form");

        return DASHBOARD_MAIN_PANEL;
    }

}
