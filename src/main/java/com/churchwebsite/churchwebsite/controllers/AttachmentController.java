package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.Attachment;
import com.churchwebsite.churchwebsite.entities.AttachmentType;
import com.churchwebsite.churchwebsite.services.AttachmentService;
import com.churchwebsite.churchwebsite.services.AttachmentTypeService;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.PaginationService;
import com.churchwebsite.churchwebsite.utils.LocalFileStorageManager;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("medias/attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;
    private final AttachmentTypeService attachmentTypeService;
    private final PaginationService paginationService;
    private final ChurchDetailService churchDetailService;

    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";

    @Autowired
    public AttachmentController(AttachmentService attachmentService,
                                AttachmentTypeService attachmentTypeService,
                                PaginationService paginationService, ChurchDetailService churchDetailService) {
        this.attachmentService = attachmentService;
        this.attachmentTypeService = attachmentTypeService;
        this.paginationService = paginationService;
        this.churchDetailService = churchDetailService;
    }

    @GetMapping("")
    public String showAllAttachments(Model model,
                                     @RequestParam(value = "page", defaultValue = "1") int page,
                                     @RequestParam(value = "size", required = false) Integer pageSize,
                                     @RequestParam(value = "sortBy", defaultValue = "", required = false) String sortBy,
                                     HttpServletRequest request){

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<Attachment> pagedAttachments = attachmentService.findAll(page, pageSize, sortBy);

        List<Attachment> attachments = pagedAttachments.getContent();

        model.addAttribute("currentPage", pagedAttachments.getNumber()+1);
        model.addAttribute("totalItems", pagedAttachments.getTotalElements());
        model.addAttribute("totalPages", pagedAttachments.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("activeDashPage", "attachments-list");
        model.addAttribute("attachments", attachments);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/form")
    public String showAttachmentForm(Model model,
                                     @ModelAttribute Attachment attachment){

        List<AttachmentType> attachmentTypes = attachmentTypeService.findAll();

        List<String> attachementNames = attachmentTypes.stream().map(AttachmentType::getAttachmentTypeName).toList();

        model.addAttribute("activeDashPage", "attachment-form");
        model.addAttribute("attachment", new Attachment());
        model.addAttribute("attachmentTypes", attachmentTypes);
        model.addAttribute("attachmentNames", attachementNames);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());


        return DASHBOARD_MAIN_PANEL;
    }


    @PostMapping("/form")
    public String processAttachmentForm(Model model,
                                        @ModelAttribute Attachment attachment,
                                        @RequestParam("attachmentFilePath") MultipartFile attachmentFilePath){

        Attachment savedAttachment = attachmentService.save(attachment, attachmentFilePath);
        return "redirect:/medias/attachments/form";
    }


    // Download filename
    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") int fileId){

        LocalFileStorageManager fileStorageManager = new LocalFileStorageManager();
        Attachment attachment = attachmentService.findById(fileId);
        Resource resource = fileStorageManager.downloadFileAsResource(attachment.getAttachmentPath());

        String contentType = null;
        try {
            contentType = Files.probeContentType(fileStorageManager.getFileStorageLocation());

            if(contentType == null) {
                contentType = "application/octet-stream"; // Default fallback
            }

            // Encode the file name for Unicode compatibility
            String encodedFileName = URLEncoder.encode(Objects.requireNonNull(resource.getFilename()), StandardCharsets.UTF_8)
                    .replace("+", "%20"); // Optional: replace '+' with '%20' for spaces

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFileName)
                    .body(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
