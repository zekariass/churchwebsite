package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.Settings;
import com.churchwebsite.churchwebsite.repositories.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Settings> findAll() {
        return settingsRepository.findAll();
    }

    public Page<Settings> findAll(int page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.asc("settingName")));

        return settingsRepository.findAll(pageable);
    }

    public Settings findById(int id) {
        return settingsRepository.findById(id).orElse(new Settings());
    }

    public void save(Settings settings) {
        settingsRepository.save(settings);
    }
}
