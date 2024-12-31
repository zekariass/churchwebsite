package com.churchwebsite.churchwebsite.entities.shopping;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shipping_plan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShippingPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shippingPlanId;

    private String planName;

    private double basePrice = 0.0;

    private double perMilePrice = 0.0;

    private double perKgPrice = 0.0;
}

