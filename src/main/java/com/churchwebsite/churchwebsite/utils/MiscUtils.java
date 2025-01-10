package com.churchwebsite.churchwebsite.utils;

import org.jsoup.Jsoup;

public class MiscUtils {

    public static String generateExcerpt(String fullText, int length) {
        if (fullText == null || fullText.isEmpty()) {
            return "";
        }

        // Convert HTML to plain text
        String plainText = Jsoup.parse(fullText).text();

        // Truncate the plain text to the desired length
        return plainText.length() > length ? plainText.substring(0, length) + "..." : plainText;

    }
}
