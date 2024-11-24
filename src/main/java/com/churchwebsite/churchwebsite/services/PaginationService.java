package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaginationService {

    private final SettingsService settingsService;

    @Value("${settings.default.page.size:10}")
    private int defaultPageSize;

    @Autowired
    public PaginationService(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    public int getPageSize(){
        Settings settings = settingsService.findBySettingName("DEFAULT_PAGE_SIZE");
        int pageSize;

        if(settings != null){
            pageSize = settings.getSettingValueInt();
        }else{
            pageSize = defaultPageSize;
        }

        return pageSize;
    }
}

