package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.Album;
import com.churchwebsite.churchwebsite.entities.Attachment;
import com.churchwebsite.churchwebsite.entities.Image;
import com.churchwebsite.churchwebsite.entities.Video;
import com.churchwebsite.churchwebsite.services.*;
import com.churchwebsite.churchwebsite.utils.LocalFileStorageManager;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/media-center")
public class PublicMediaCenterController {

    private final ChurchDetailService churchDetailService;
    private final PaginationService paginationService;
    private final AttachmentService attachmentService;
    private final AlbumService albumService;
    private final VideoService videoService;
    private final AttachmentTypeService attachmentTypeService;

    private final String PUBLIC_CONTENT = "layouts/base";


    private final ImageService imageService;

    public PublicMediaCenterController(ChurchDetailService churchDetailService,
                                       PaginationService paginationService,
                                       ImageService imageService,
                                       AlbumService albumService,
                                       VideoService videoService,
                                       AttachmentTypeService attachmentTypeService,
                                       AttachmentService attachmentService){
        this.churchDetailService = churchDetailService;
        this.imageService = imageService;
        this.paginationService = paginationService;
        this.albumService = albumService;
        this.videoService = videoService;

        this.attachmentService = attachmentService;
        this.attachmentTypeService = attachmentTypeService;
    }

    @GetMapping("/options")
    public String showMediaOption(Model model){
        model.addAttribute("activeContentPage", "media-center-options");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());

        return PUBLIC_CONTENT;
    }


    @GetMapping("/albums")
    public String showAlbumList(Model model,
                                @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                @RequestParam(value = "size", required = false) Integer pageSize,
                                AttachmentService attachmentService,
                                AttachmentTypeService attachmentTypeService,
                                HttpServletRequest request){

        String baseMediaPath = File.separator + Paths.get("media/centre") + File.separator;

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<Album> pagedAlbums = albumService.getAlbumList(page, pageSize, Sort.by(Sort.Order.desc("creationTime")));
        List<Album> albums = pagedAlbums.getContent();

        model.addAttribute("churchDetail", churchDetailService);

        model.addAttribute("activeContentPage", "albums-list");
        model.addAttribute("currentPage", pagedAlbums.getNumber() + 1);
        model.addAttribute("totalItems", pagedAlbums.getTotalElements());
        model.addAttribute("totalPages", pagedAlbums.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("albums", albums);
        model.addAttribute("baseMediaPath", baseMediaPath);

        return PUBLIC_CONTENT;
    }

//    @GetMapping("/videos")
//    public String showVideosList(Model model,
//                                 @RequestParam(value = "page", required = false, defaultValue = "1") int page,
//                                 @RequestParam(value = "size", required = false) Integer pageSize,
//                                 @RequestParam(value = "sortBy", defaultValue = "uploadTime") String sortBy,
//                                 HttpServletRequest request){
////
////        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();
////
////        Page<Image> pagedVideoList = imageService.getVideoList(page, pageSize, sortBy);
////        List<Image> videoList = pagedVideoList.getContent();
////
////        model.addAttribute("activeContentPage", "videos-list");
////        model.addAttribute("churchDetail", churchDetail);
////
////        model.addAttribute("videos", videoList);
////
////        model.addAttribute("currentPage", pagedVideoList.getNumber()+1);
////        model.addAttribute("totalItems", pagedVideoList.getTotalElements());
////        model.addAttribute("totalPages", pagedVideoList.getTotalPages());
//        model.addAttribute("pageSize", pageSize);
//        model.addAttribute("currentUrl", request.getRequestURL());
//        model.addAttribute("sortBy", sortBy);
//
//        return PUBLIC_CONTENT;
//    }

//    @GetMapping("/albums")
//    public String showImagesList(Model model,
//                                 @RequestParam(value = "page", required = false, defaultValue = "1") int page,
//                                 @RequestParam(value = "size", required = false) Integer pageSize,
//                                 @RequestParam(value = "sortBy", defaultValue = "uploadTime") String sortBy,
//                                 HttpServletRequest request){
//
//        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();
//
//        Page<Image> pagedImageMediaList = imageService.getImagesAlbums(page, pageSize, sortBy);
//        List<Image> imageMediaAlbumList = pagedImageMediaList.getContent();
//
//        model.addAttribute("activeContentPage", "images-list");
//        model.addAttribute("churchDetail", churchDetail);
//
//        model.addAttribute("albumsList", imageMediaAlbumList);
//
//        model.addAttribute("currentPage", pagedImageMediaList.getNumber()+1);
//        model.addAttribute("totalItems", pagedImageMediaList.getTotalElements());
//        model.addAttribute("totalPages", pagedImageMediaList.getTotalPages());
//        model.addAttribute("pageSize", pageSize);
//        model.addAttribute("currentUrl", request.getRequestURL());
//        model.addAttribute("sortBy", sortBy);
//
//        return PUBLIC_CONTENT;
//    }

//    @GetMapping("/files")
//    public String showFilesList(Model model,
//                                @RequestParam(value = "page", defaultValue = "1") int page,
//                                @RequestParam(value = "size", required = false) Integer pageSize,
//                                @RequestParam(value = "sortBy", defaultValue = "", required = false) String sortBy,
//                                HttpServletRequest request){
//
//            pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();
//
//            Page<Attachment> pagedAttachments = attachmentService.findAll(page, pageSize, sortBy);
//
//            List<Attachment> attachments = pagedAttachments.getContent();
//
//            model.addAttribute("currentPage", pagedAttachments.getNumber()+1);
//            model.addAttribute("totalItems", pagedAttachments.getTotalElements());
//            model.addAttribute("totalPages", pagedAttachments.getTotalPages());
//            model.addAttribute("pageSize", pageSize);
//            model.addAttribute("currentUrl", request.getRequestURL());
//            model.addAttribute("activeContentPage", "files-list");
//            model.addAttribute("attachments", attachments);
//            model.addAttribute("churchDetail", churchDetail);
//            model.addAttribute("sortBy", sortBy);
//
//            return PUBLIC_CONTENT;
//    }

    @GetMapping("/albums/{id}/images")
    public String showAlbumDetail(@PathVariable(value = "id", required = false) int albumId,
                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                  @RequestParam(value = "size", required = false) Integer pageSize,
                                  @RequestParam(value = "sortBy", defaultValue = "", required = false) String sortBy,
                                  Model model,
                                  HttpServletRequest request){

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<Image> pagedAlbumImages = imageService.findByAlbum(page, pageSize, sortBy, albumId);

        List<Image> images = pagedAlbumImages.getContent();

        model.addAttribute("currentPage", pagedAlbumImages.getNumber()+1);
        model.addAttribute("totalItems", pagedAlbumImages.getTotalElements());
        model.addAttribute("totalPages", pagedAlbumImages.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("images", images);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("sortBy", sortBy);

        Album album = albumService.getAlbumById(albumId);
//        String baseMediaPath = File.separator + Paths.get("media/centre") + File.separator;
//
        model.addAttribute("album", album);
        model.addAttribute("activeContentPage", "album-detail");
//        model.addAttribute("baseMediaPath", baseMediaPath);

        return PUBLIC_CONTENT;
    }

    @GetMapping("/videos")
    public String showVideoList(Model model,
                                @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                @RequestParam(value = "size", required = false) Integer pageSize,
                                HttpServletRequest request){

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<Video> pagesVideoList = videoService.getVideoList(page, pageSize, Sort.by(Sort.Order.desc("uploadTime")));
        List<Video> videoList = pagesVideoList.getContent();

        model.addAttribute("activeContentPage", "videos-list");
        model.addAttribute("currentPage", pagesVideoList.getNumber() + 1);
        model.addAttribute("totalItems", pagesVideoList.getTotalElements());
        model.addAttribute("totalPages", pagesVideoList.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("videos", videoList);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());


        return PUBLIC_CONTENT;
    }


//    ==================== ATTACHMENT ================================

    @GetMapping("/files")
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
        model.addAttribute("activeContentPage", "attachments-list");
        model.addAttribute("attachments", attachments);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("sortBy", sortBy);

        return PUBLIC_CONTENT;
    }

    // Download filename
    @GetMapping("/files/download/{fileId}")
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
            String encodedFileName = URLEncoder.encode(resource.getFilename(), StandardCharsets.UTF_8.toString())
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
