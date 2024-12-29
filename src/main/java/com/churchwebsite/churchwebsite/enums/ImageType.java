package com.churchwebsite.churchwebsite.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageType {
    THUMBNAIL("Thumbnail"), GALLERY("Gallery");

    private final String displayName;
}
