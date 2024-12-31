package com.churchwebsite.churchwebsite.utils;

import com.churchwebsite.churchwebsite.services.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Currency;
import java.util.Locale;

@Component
public class LocaleUtil {

    private final SettingsService settingsService;

    @Autowired
    public LocaleUtil(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    public Currency getCurrency(){
        String localLanguageCode = settingsService.findBySettingName("LOCALE_LANGUAGE_CODE").getSettingValueChar();
        String localCountryCode = settingsService.findBySettingName("LOCALE_COUNTRY_CODE").getSettingValueChar();

        localLanguageCode = localLanguageCode != null ? localLanguageCode: "en";
        localCountryCode = localCountryCode != null ? localCountryCode: "GB";

        Locale locale = new Locale.Builder()
                .setLanguage(localLanguageCode)
                .setRegion(localCountryCode)
                .build();
//                (localLanguageCode, localCountryCode);

        return Currency.getInstance(locale);
    }
}
