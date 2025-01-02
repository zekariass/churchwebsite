package com.churchwebsite.churchwebsite.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    SUBMITTED("Submitted"), COMPLETED("Completed"), CANCELLED("Cancelled");

    private final String displayName;
}
