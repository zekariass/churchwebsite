package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.Album;
import com.churchwebsite.churchwebsite.entities.Image;
import com.churchwebsite.churchwebsite.services.AlbumService;
import com.churchwebsite.churchwebsite.services.ImageService;
import com.churchwebsite.churchwebsite.services.AttachmentTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("images")
public class ImageController {

    private final ImageService imageService;
    private final AlbumService albumService;
    private final AttachmentTypeService attachmentTypeService;
    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";

    @Autowired
    public ImageController(ImageService imageService,
                           AlbumService albumService,
                           AttachmentTypeService attachmentTypeService){
        this.imageService = imageService;
        this.albumService = albumService;
        this.attachmentTypeService = attachmentTypeService;
    }


    @GetMapping("/form")
    public String showImageForm(Model model,
                                @RequestParam(value = "for", required = false) String purpose,
                                @RequestParam(value = "forAlbumId", defaultValue = "0") Integer forAlbumId

                                ){

        List<Album> albums = albumService.getAlbumList();
//        List<AttachmentType> attachmentTypes = attachmentTypeService.getImageTypeList();

        model.addAttribute("activeDashPage", "image-form");
        model.addAttribute("albums", albums);
//        model.addAttribute("attachmentTypes", attachmentTypes);
        model.addAttribute("album", new Album());
        model.addAttribute("image", new Image());


        if(forAlbumId == null || forAlbumId == 0){
            model.addAttribute("forAlbumId", 0);
        }else{
            model.addAttribute("forAlbumId", forAlbumId);
        }




        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/form")
    public  String processImageForm(@RequestParam(value = "for", required = false, defaultValue = "general") String purpose,
                                    @Valid @ModelAttribute("image") Image image,
                                    BindingResult bindingResult,
                                    @RequestParam(value = "imagePaths", required = false) List<MultipartFile> multipartFiles,
                                    @RequestParam(value = "forAlbumId", defaultValue = "0") int forAlbumId,
                                    Model model){

        // Check if new album was to be created and the name is null or empty
        if(image.getAlbum() != null && image.getAlbum().getAlbumId() == 0
            && (Objects.equals(image.getAlbum().getAlbumName(), "")
                    || image.getAlbum().getAlbumName() == null)){

            bindingResult.rejectValue("album", "album", "Album must be selected or created new one.");
        }

        // Check for validation errors
        if(bindingResult.hasErrors()){
            List<Album> albums = albumService.getAlbumList();
//            List<AttachmentType> attachmentTypes = attachmentTypeService.getImageTypeList();
            model.addAttribute("activeDashPage", "image-form");
            model.addAttribute("albums", albums);
//            model.addAttribute("attachmentTypes", attachmentTypes);
            model.addAttribute("album", new Album());
            model.addAttribute("forAlbumId", forAlbumId);
            model.addAttribute("image", image);

            return DASHBOARD_MAIN_PANEL;

        }else {
            if (purpose.equals("blog") || purpose.equals("information") || purpose.equals("news")) {
                model.addAttribute("activeDashPage", "image-links-list");
            } else {
                model.addAttribute("activeDashPage", "albums-list");
            }

            // Check if existing album is selected or new one is created
            Album savedAlbum;
            if (image.getAlbum().getAlbumId() == 0) {
                Album album = image.getAlbum();
                album.setArchived(false);
                savedAlbum = albumService.save(album);
            } else {
                savedAlbum = image.getAlbum();
            }

            image.setAlbum(savedAlbum);
            image.setArchived(false);
            List<String> imagePaths = new ArrayList<>();

            // Check if single or multiple files selected then save the files in the file system
           if (multipartFiles.size() == 1) {
                String path = imageService.save(image, multipartFiles.getFirst());
                imagePaths.add(path);
            } else {
                for (MultipartFile file : multipartFiles) {
                    Image newImage = image.clone();
                    String path = imageService.save(newImage, file);
                    imagePaths.add(path);
                }
            }
            model.addAttribute("imagePaths", imagePaths);

        }
            return "redirect:/images/albums";
        }
}
