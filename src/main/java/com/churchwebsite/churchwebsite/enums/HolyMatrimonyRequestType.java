package com.churchwebsite.churchwebsite.enums;

public enum HolyMatrimonyRequestType {
    MATRIMONY_ONLY("Marriage Only"), MATRIMONY_AND_HALL("Marriage and Hall");
    private final String displayName;

    HolyMatrimonyRequestType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
