package com.churchwebsite.churchwebsite.services.shopping;

import com.churchwebsite.churchwebsite.entities.shopping.ShippingPlan;
import com.churchwebsite.churchwebsite.repositories.Shopping.ShippingPlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShippingPlanService {

    private final ShippingPlanRepository shippingPlanRepository;

    public ShippingPlanService(ShippingPlanRepository shippingPlanRepository) {
        this.shippingPlanRepository = shippingPlanRepository;
    }

    public List<ShippingPlan> findAll() {
        return shippingPlanRepository.findAll();
    }
}
