package com.churchwebsite.churchwebsite.enums;

public enum MembershipPaymentMethod {
    DIRECT_DEBIT("Direct Debit"),
    CASH("Cash (Every Month)");

    private final String displayName;


    MembershipPaymentMethod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
