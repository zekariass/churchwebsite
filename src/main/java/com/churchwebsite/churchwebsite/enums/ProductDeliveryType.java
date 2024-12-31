package com.churchwebsite.churchwebsite.enums;

//@Getter
//@AllArgsConstructor
public enum ProductDeliveryType {
    COLLECT("Collect"),
    DELIVERY("Delivery"),
    DELIVERY_OR_COLLECT("Delivery and Collect");

    private final String displayName;

    ProductDeliveryType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
