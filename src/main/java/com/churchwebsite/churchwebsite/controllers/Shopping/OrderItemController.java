package com.churchwebsite.churchwebsite.controllers.Shopping;

import com.churchwebsite.churchwebsite.entities.shopping.OrderItem;
import com.churchwebsite.churchwebsite.enums.OrderStatus;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.shopping.OrderItemService;
import com.churchwebsite.churchwebsite.utils.LocaleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dashboard/orders/items")
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final LocaleUtil localeUtil;
    private  final ChurchDetailService churchDetailService;

    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";

    @Autowired
    public OrderItemController(OrderItemService orderItemService, LocaleUtil localeUtil, ChurchDetailService churchDetailService) {
        this.orderItemService = orderItemService;
        this.localeUtil = localeUtil;
        this.churchDetailService = churchDetailService;
    }


    @GetMapping("/detail/{id}")
    public String getOrderItemById(@PathVariable Integer id, Model model) {
        model.addAttribute("orderItem", orderItemService.getOrderItemById(id).orElse(null));
        model.addAttribute("activeDashPage", "order-item-detail");
        model.addAttribute("currencySymbol", localeUtil.getCurrency().getSymbol());
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Order Item Detail");


        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/edit/form/{id}")
    public String orderItemForm(@PathVariable Integer id,
                                Model model) {
        model.addAttribute("orderItem", orderItemService.getOrderItemById(id).orElse(null));
        model.addAttribute("orderStatuses", OrderStatus.values());
        model.addAttribute("activeDashPage", "order-item-edit-form");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Order Form");

        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/edit/form/process")
    public String updateOrderItem(@ModelAttribute OrderItem updatedOrderItem,
                                  Model model) {
        OrderItem orderItem = orderItemService.saveOrderItem(updatedOrderItem);
        return "redirect:/dashboard/orders/items/detail/"+orderItem.getOrderItemId();
    }

}
