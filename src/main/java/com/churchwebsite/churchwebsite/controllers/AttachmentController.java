package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.Attachment;
import com.churchwebsite.churchwebsite.entities.AttachmentType;
import com.churchwebsite.churchwebsite.services.AttachmentService;
import com.churchwebsite.churchwebsite.services.AttachmentTypeService;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.PaginationService;
import com.churchwebsite.churchwebsite.services.storage.CloudinaryFileStorageManager;
import com.churchwebsite.churchwebsite.services.storage.LocalFileStorageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("medias/attachments")
public class AttachmentController {

    private final Logger logger = LoggerFactory.getLogger(AttachmentController.class);

    private final AttachmentService attachmentService;
    private final AttachmentTypeService attachmentTypeService;
    private final PaginationService paginationService;
    private final ChurchDetailService churchDetailService;

    private final CloudinaryFileStorageManager cloudinaryFileStorageManager;


//    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";
    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-layouts/dash-base";

    @Value("${file.storage.type}")
    private String fileStorageType;

    @Autowired
    public AttachmentController(AttachmentService attachmentService,
                                AttachmentTypeService attachmentTypeService,
                                PaginationService paginationService, ChurchDetailService churchDetailService, CloudinaryFileStorageManager cloudinaryFileStorageManager) {
        this.attachmentService = attachmentService;
        this.attachmentTypeService = attachmentTypeService;
        this.paginationService = paginationService;
        this.churchDetailService = churchDetailService;
        this.cloudinaryFileStorageManager = cloudinaryFileStorageManager;
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
        model.addAttribute("pageTitle", "Attachments List");


        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/form")
    public String showAttachmentForm(Model model){

        List<AttachmentType> attachmentTypes = attachmentTypeService.findAll();

        List<String> attachementNames = attachmentTypes.stream().map(AttachmentType::getAttachmentTypeName).toList();

        model.addAttribute("activeDashPage", "attachment-form");
        model.addAttribute("attachment", new Attachment());
        model.addAttribute("attachmentTypes", attachmentTypes);
        model.addAttribute("attachmentNames", attachementNames);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Attachments Form");


        return DASHBOARD_MAIN_PANEL;
    }


    @PostMapping("/form")
    public String processAttachmentForm(Model model,
                                        @Valid @ModelAttribute Attachment attachment,
                                        BindingResult result,
                                        @RequestParam(value = "attachmentFilePath", required = false) MultipartFile attachmentFilePath){

        if(attachment.getAttachmentId() == 0 && (result.hasErrors() || attachmentFilePath.isEmpty())){

            if(attachmentFilePath.isEmpty()){
                model.addAttribute("attachmentFilePathError", "You must select a file.");
            }

            List<AttachmentType> attachmentTypes = attachmentTypeService.findAll();

            List<String> attachementNames = attachmentTypes.stream().map(AttachmentType::getAttachmentTypeName).toList();

            model.addAttribute("activeDashPage", "attachment-form");
            model.addAttribute("attachment", attachment);
            model.addAttribute("attachmentTypes", attachmentTypes);
            model.addAttribute("attachmentNames", attachementNames);
            model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
            model.addAttribute("pageTitle", "Attachments Form");


            return DASHBOARD_MAIN_PANEL;
        }

        if(attachment.getAttachmentId() > 0){
            attachmentService.saveExisting(attachment);
        }else{
            Attachment savedAttachment = attachmentService.save(attachment, attachmentFilePath);
        }
        return "redirect:/medias/attachments?sortBy=attachmentTime";
    }


    // Download filename
    @GetMapping("/download/{fileId}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable("fileId") int fileId){

        Attachment attachment = attachmentService.findById(fileId);

        if(fileStorageType.equalsIgnoreCase("cloudinary")){
            try {
                InputStream fileStream = new URL(attachment.getAttachmentPath()).openStream(); //cloudinaryFileStorageManager.downloadFileAsResource(attachment.getPublicId());
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + attachment.getPublicId())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(new InputStreamResource(fileStream));
            } catch (Exception e) {
                logger.error("ERROR ==============================: {}", e.getMessage());
                return ResponseEntity.internalServerError().build();
            }
        }else{
            LocalFileStorageManager fileStorageManager = new LocalFileStorageManager();
            InputStreamResource resource = (InputStreamResource) fileStorageManager.downloadFileAsResource(attachment.getAttachmentPath());

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

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Integer id){
        attachmentService.deleteById(id);

        return "redirect:/medias/attachments";
    }


    @GetMapping("/edit/{id}")
    public String editAttachment(@PathVariable("id") Integer id, Model model){
        Attachment attachment = attachmentService.findById(id);

        List<AttachmentType> attachmentTypes = attachmentTypeService.findAll();

        List<String> attachementNames = attachmentTypes.stream().map(AttachmentType::getAttachmentTypeName).toList();

        model.addAttribute("activeDashPage", "attachment-form");
        model.addAttribute("attachment", attachment);
        model.addAttribute("attachmentTypes", attachmentTypes);
        model.addAttribute("attachmentNames", attachementNames);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Attachments Form");


        return DASHBOARD_MAIN_PANEL;
    }
}
