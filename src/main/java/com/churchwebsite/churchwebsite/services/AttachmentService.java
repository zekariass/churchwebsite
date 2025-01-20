package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.Attachment;
import com.churchwebsite.churchwebsite.repositories.AttachmentRepository;
import com.churchwebsite.churchwebsite.services.storage.CloudinaryFileStorageManager;
import com.churchwebsite.churchwebsite.services.storage.LocalFileStorageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AttachmentService {

    private final Logger logger = LoggerFactory.getLogger(AttachmentService.class);

    private final AttachmentRepository attachmentRepository;
    private final CloudinaryFileStorageManager cloudinaryFileStorageManager;


    @Value("${local.file.attachment-dir}")
    private String localAttachmentUploadDir;

    @Value("${cloudinary.file.attachment-dir}")
    private String cloudinaryAttachmentUploadDir;

    @Value("${file.storage.type}")
    private String fileStorageType;

    @Autowired
    public AttachmentService(AttachmentRepository attachmentRepository, CloudinaryFileStorageManager cloudinaryFileStorageManager) {
        this.attachmentRepository = attachmentRepository;
        this.cloudinaryFileStorageManager = cloudinaryFileStorageManager;
    }


    public Page<Attachment> findAll(int page, int pageSize, String sortBy) {
        Pageable pageable = getPageable(page, pageSize, sortBy);
        return attachmentRepository.findAll(pageable);
    }


    public List<Attachment> findAll() {
        return attachmentRepository.findAll();
    }


    public Attachment save(Attachment attachment, MultipartFile multipartFile) {

        String filePath;
        Map result;

        if(fileStorageType.equalsIgnoreCase("cloudinary")){
            if(Objects.requireNonNull(multipartFile.getContentType()).contains("image")){
                result = cloudinaryFileStorageManager.storeFile(multipartFile, cloudinaryAttachmentUploadDir);
            }else{
                result = cloudinaryFileStorageManager.storeRawFile(multipartFile, cloudinaryAttachmentUploadDir);
            }
            filePath = (String) result.get("secure_url");
            attachment.setPublicId((String) result.get("public_id"));
        }else{
            LocalFileStorageManager localFileStorageManager = new LocalFileStorageManager(localAttachmentUploadDir);
            filePath = localFileStorageManager.storeFile(multipartFile);
            attachment.setPublicId(null);
        }

        System.out.println("============================ filePath========: "+ filePath);


        attachment.setAttachmentPath(filePath);
        attachment.setArchived(false);

        return attachmentRepository.save(attachment);
    }

    public Attachment findById(int fileId) {
        return attachmentRepository.findById(fileId).orElseThrow(
                () -> new RuntimeException("No attachment found.")
        );
    }


    public Page<Attachment> findByArchived(Boolean archived, int page, Integer pageSize, String sortBy) {
        Pageable pageable = getPageable(page, pageSize, sortBy);
        return attachmentRepository.findByArchived(archived, pageable);
    }

    public Pageable getPageable(int page, Integer pageSize, String sortBy) {
        Pageable pageable;
        if(sortBy == null || sortBy.isEmpty() || sortBy.equals("attachmentTime")){
            if(sortBy == null || sortBy.isEmpty()){
                sortBy = "attachmentTime";
            }
            pageable = PageRequest.of(page -1, pageSize, Sort.by(Sort.Order.desc(sortBy)));
        }else{
            pageable = PageRequest.of(page -1, pageSize, Sort.by(Sort.Order.by(sortBy)));
        }
        return pageable;
    }

    public void deleteById(Integer id) {
        Attachment attachment = attachmentRepository.findById(id).orElse(null);

        if(fileStorageType.equalsIgnoreCase("cloudinary")){
            try {
                if(attachment != null){
                    cloudinaryFileStorageManager.deleteFile(attachment.getPublicId());
                }
            } catch (IOException e) {
                logger.error("ERROR: ==========================> {}", e.getMessage());
            }
        }else{
            LocalFileStorageManager fileStorageManager = new LocalFileStorageManager(localAttachmentUploadDir);

            if(attachment != null){
                String fullAttachmentPath = attachment.getAttachmentPath();
                String[] fileNameSplits = fullAttachmentPath.split("/");
                String fileName = fileNameSplits[fileNameSplits.length-1];
                fileStorageManager.deleteFile(fileName);
            }
        }

        attachmentRepository.deleteById(id);
    }

    public Page<Attachment> findByAttachmentNameContaining(String name, int page, Integer pageSize, String sortBy) {
        Pageable pageable = getPageable(page, pageSize, sortBy);
        return attachmentRepository.findByAttachmentNameContaining(name, pageable);
    }

    public void saveExisting(Attachment attachment) {
        attachmentRepository.save(attachment);
    }
}
