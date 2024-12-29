package com.churchwebsite.churchwebsite.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShippingStatus {
    PENDING("Pending"), SHIPPED("Shipped"), COLLECTED("Collected"), COMPLETED("Completed"), CANCELLED("Cancelled");

    private final String displayName;
}
