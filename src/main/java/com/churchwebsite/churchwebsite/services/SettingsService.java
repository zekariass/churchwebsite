package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.Settings;
import com.churchwebsite.churchwebsite.repositories.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {

    private final SettingsRepository settingsRepository;

    @Autowired
    public SettingsService(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public Settings findBySettingName(String settingName){
        return settingsRepository.findBySettingName(settingName);
    }

    public void deleteById(int blogId) {
        settingsRepository.deleteById(blogId);
    }
}
