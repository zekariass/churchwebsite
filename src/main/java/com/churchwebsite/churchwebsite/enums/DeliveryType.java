package com.churchwebsite.churchwebsite.enums;

public enum DeliveryType {
    DELIVERY("Delivery"), COLLECTION("Collection");

    private final String displayName;

    DeliveryType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
