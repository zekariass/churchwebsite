package com.churchwebsite.churchwebsite.enums;

public enum ServiceStatus {
    REQUEST("Request"), APPROVED("Approved"), REJECTED("Rejected"), COMPLETED("Completed");
    private final String displayName;

    ServiceStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
