package com.churchwebsite.churchwebsite.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatus {
    PENDING("Pending"), COMPLETED("Completed"), FAILED("Failed");

    private final String displayName;
}
