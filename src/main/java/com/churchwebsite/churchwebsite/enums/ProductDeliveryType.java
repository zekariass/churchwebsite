package com.churchwebsite.churchwebsite.enums;

//@Getter
//@AllArgsConstructor
public enum ProductDeliveryType {
    COLLECT("Collect"),
    SHIPPING("Shipping"),
    SHIPPING_OR_COLLECT("Shipping and Collect");

    private final String displayName;

    ProductDeliveryType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
