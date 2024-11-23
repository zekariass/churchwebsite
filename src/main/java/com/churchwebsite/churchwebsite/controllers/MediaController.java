package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.Album;
import com.churchwebsite.churchwebsite.entities.Media;
import com.churchwebsite.churchwebsite.entities.MediaType;
import com.churchwebsite.churchwebsite.services.AlbumService;
import com.churchwebsite.churchwebsite.services.MediaService;
import com.churchwebsite.churchwebsite.services.MediaTypeService;
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
@RequestMapping("medias")
public class MediaController {

    private final MediaService mediaService;
    private final AlbumService albumService;
    private final MediaTypeService mediaTypeService;
    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";

    @Autowired
    public MediaController(MediaService mediaService,
                           AlbumService albumService,
                           MediaTypeService mediaTypeService){
        this.mediaService = mediaService;
        this.albumService = albumService;
        this.mediaTypeService = mediaTypeService;
    }


    @GetMapping("/form")
    public String showMediaForm(Model model,
                                @RequestParam(value = "for", required = false) String purpose){

        List<Album> albums = albumService.getAlbumList();
        List<MediaType> mediaTypes = mediaTypeService.getMediaTypeList();

        model.addAttribute("activeDashPage", "media-form");
        model.addAttribute("albums", albums);
        model.addAttribute("mediaTypes", mediaTypes);
        model.addAttribute("album", new Album());
        model.addAttribute("media", new Media());


        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/form")
    public  String processMediaForm(@RequestParam(value = "for", required = false, defaultValue = "general") String purpose,
                                    @Valid @ModelAttribute("media") Media media,
                                    BindingResult bindingResult,
                                    @RequestParam(value = "mediaPaths", required = false) List<MultipartFile> multipartFiles,
                                    Model model){

        // Check if new album was to be created and the name is null or empty
        if(media.getAlbum() != null && media.getAlbum().getAlbumId() == 0
            && (Objects.equals(media.getAlbum().getAlbumName(), "")
                    || media.getAlbum().getAlbumName() == null)){

            bindingResult.rejectValue("album", "album", "Album must be selected or created new one.");
        }

        // Check for validation errors
        if(bindingResult.hasErrors()){
            List<Album> albums = albumService.getAlbumList();
            List<MediaType> mediaTypes = mediaTypeService.getMediaTypeList();
            model.addAttribute("activeDashPage", "media-form");
            model.addAttribute("albums", albums);
            model.addAttribute("mediaTypes", mediaTypes);
            model.addAttribute("album", new Album());
            model.addAttribute("media", media);

            return DASHBOARD_MAIN_PANEL;

        }else {
            if (purpose.equals("blog") || purpose.equals("information") || purpose.equals("news")) {
                model.addAttribute("activeDashPage", "media-links-list");
            } else {
                model.addAttribute("activeDashPage", "albums-list");
            }

            // Check if existing album is selected or new one is created
            Album savedAlbum;
            if (media.getAlbum().getAlbumId() == 0) {
                Album album = media.getAlbum();
                album.setArchived(false);
                savedAlbum = albumService.save(album);
            } else {
                savedAlbum = media.getAlbum();
            }

            media.setAlbum(savedAlbum);
            media.setArchived(false);
            List<String> mediaPaths = new ArrayList<>();

            // Check if single or multiple files selected then save the files in the file system
           if (multipartFiles.size() == 1) {
                String path = mediaService.save(media, multipartFiles.getFirst());
                mediaPaths.add(path);
            } else {
                for (MultipartFile file : multipartFiles) {
                    Media newMedia = media.clone();
                    String path = mediaService.save(newMedia, file);
                    mediaPaths.add(path);
                }
            }
            model.addAttribute("mediaPaths", mediaPaths);
        }

            return "redirect:/medias/albums";
        }
}
