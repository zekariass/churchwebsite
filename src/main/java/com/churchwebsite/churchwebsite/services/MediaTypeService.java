package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.MediaType;
import com.churchwebsite.churchwebsite.repositories.MediaTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediaTypeService {

    private final MediaTypeRepository mediaTypeRepository;

    @Autowired
    public MediaTypeService(MediaTypeRepository mediaTypeRepository) {
        this.mediaTypeRepository = mediaTypeRepository;
    }

    public List<MediaType> getMediaTypeList() {
        return mediaTypeRepository.findAll();
    }
}
