package com.churchwebsite.churchwebsite.enums;

public enum BaptismRequestType {
    BAPTISM_ONLY("Baptism Only"),
    BAPTISM_AND_HALL("Baptism and Hall");
    private final String displayName;

    BaptismRequestType(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
