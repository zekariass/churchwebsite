package com.churchwebsite.churchwebsite.enums;

public enum ProductListingStatus {
    LISTED("Listed"), NOT_LISTED("Not Listed");

    private final String displayName;

    ProductListingStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
