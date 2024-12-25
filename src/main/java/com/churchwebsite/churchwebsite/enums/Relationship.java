package com.churchwebsite.churchwebsite.enums;

public enum Relationship {
    MOTHER("Mother"),
    FATHER("Father"),
    SON("Son"),
    DAUGHTER("Daughter"),
    BROTHER("Brother"),
    SISTER("Sister"),
    GRANDMOTHER("Grandmother"),
    GRANDFATHER("Grandfather"),
    RELATIVE("Relative"),
    OTHER("Other");

    private final String displayName;

    Relationship(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
