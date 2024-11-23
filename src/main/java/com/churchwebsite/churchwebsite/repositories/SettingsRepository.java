package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.Settings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsRepository extends JpaRepository<Settings, Integer> {
    Settings findBySettingName(String settingName);
}
