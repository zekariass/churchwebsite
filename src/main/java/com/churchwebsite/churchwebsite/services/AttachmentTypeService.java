package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.AttachmentType;
import com.churchwebsite.churchwebsite.repositories.AttachmentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttachmentTypeService {

    private final AttachmentTypeRepository attachmentTypeRepository;

    @Autowired
    public AttachmentTypeService(AttachmentTypeRepository attachmentTypeRepository) {
        this.attachmentTypeRepository = attachmentTypeRepository;
    }

    public List<AttachmentType> getMediaTypeList() {
        return attachmentTypeRepository.findAll();
    }
}
