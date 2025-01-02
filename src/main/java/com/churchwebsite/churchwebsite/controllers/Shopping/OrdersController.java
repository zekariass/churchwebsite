package com.churchwebsite.churchwebsite.controllers.Shopping;

import com.churchwebsite.churchwebsite.entities.shopping.Orders;
import com.churchwebsite.churchwebsite.enums.OrderStatus;
import com.churchwebsite.churchwebsite.services.PaginationService;
import com.churchwebsite.churchwebsite.services.shopping.OrdersService;
import com.churchwebsite.churchwebsite.utils.LocaleUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dashboard/orders")
public class OrdersController {

    private final OrdersService ordersService;
    private final PaginationService paginationService;
    private final LocaleUtil localeUtil;

    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";

    @Autowired
    public OrdersController(OrdersService ordersService, PaginationService paginationService, LocaleUtil localeUtil) {
        this.ordersService = ordersService;
        this.paginationService = paginationService;
        this.localeUtil = localeUtil;
    }

    @GetMapping("")
    public String getAllOrders(Model model,
                               @RequestParam(value = "shippingId", required = false) Integer shippingId,
                               @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                               @RequestParam(value = "size", required = false) Integer pageSize,
                               @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                               HttpServletRequest request){

        List<Orders> orders;
        if(shippingId != null && shippingId > 0){
            orders = ordersService.getOrdersByShippingId(shippingId);
        }
        else {
            pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

            Page<Orders> pagedOrders = ordersService.getAllOrders(page, pageSize, sortBy);
            orders = pagedOrders.getContent();


            model.addAttribute("currentPage", pagedOrders.getNumber()+1);
            model.addAttribute("totalItems", pagedOrders.getTotalElements());
            model.addAttribute("totalPages", pagedOrders.getTotalPages());
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("currentUrl", request.getRequestURL());
            model.addAttribute("sortBy", sortBy);
        }



        model.addAttribute("activeDashPage", "orders-list");
        model.addAttribute("orders", orders);
        model.addAttribute("currencySymbol", localeUtil.getCurrency().getSymbol());


        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/detail/{id}")
    public String getOrderById(@PathVariable Integer id, Model model) {
        model.addAttribute("currencySymbol", localeUtil.getCurrency().getSymbol());
        model.addAttribute("activeDashPage", "order-detail");
        model.addAttribute("order", ordersService.getOrderById(id).orElse(null));
        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/edit/form/{id}")
    public String orderItemForm(@PathVariable Integer id,
                                Model model) {
        model.addAttribute("order", ordersService.getOrderById(id).orElse(null));
        model.addAttribute("activeDashPage", "order-edit-form");
        model.addAttribute("orderStatuses", OrderStatus.values());
        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/edit/form/process")
    public String updateOrderItem(@ModelAttribute Orders updatedOrder,
                                  Model model) {
        Orders order = ordersService.saveOrder(updatedOrder);
        return "redirect:/dashboard/orders/detail/"+ order.getOrderId();
    }

}

