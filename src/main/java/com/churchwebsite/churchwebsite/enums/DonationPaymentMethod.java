package com.churchwebsite.churchwebsite.enums;

public enum DonationPaymentMethod {
    CREDIT_OR_DEBIT_CARD("Credit/Debit Card"),
    CHECK("Paper Check"),
    DIRECT_DEBIT_MONTHLY("Direct Debit (Monthly)");

    private final String displayName;


    DonationPaymentMethod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
