package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.Attachment;
import com.churchwebsite.churchwebsite.repositories.AttachmentRepository;
import com.churchwebsite.churchwebsite.utils.LocalFileStorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;

    @Value("${local.file.attachment-dir}")
    private String attachmentUploadDir;

    @Autowired
    public AttachmentService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }


    public Page<Attachment> findAll(int page, int pageSize, String sortBy) {
        Pageable pageable = getPageable(page, pageSize, sortBy);
        return attachmentRepository.findAll(pageable);
    }


    public List<Attachment> findAll() {
        return attachmentRepository.findAll();
    }


    public Attachment save(Attachment attachment, MultipartFile multipartFile) {
        LocalFileStorageManager localFileStorageManager = new LocalFileStorageManager(attachmentUploadDir);
        String filePath = localFileStorageManager.storeFile(multipartFile);
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
}
