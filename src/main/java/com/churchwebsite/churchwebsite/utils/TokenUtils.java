package com.churchwebsite.churchwebsite.utils;

import java.util.UUID;

public class TokenUtils {
    public static String generatePasswordResetToken(){
        return UUID.randomUUID().toString();
    }
}
