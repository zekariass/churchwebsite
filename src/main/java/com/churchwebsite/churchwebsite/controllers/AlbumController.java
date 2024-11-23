package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.Album;
import com.churchwebsite.churchwebsite.entities.Media;
import com.churchwebsite.churchwebsite.services.AlbumService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("medias/albums")
public class AlbumController {

    private final AlbumService albumService;

    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("")
    public String showAlbumList(Model model,
                                @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                @RequestParam(value = "size", defaultValue = "4", required = false) int pageSize,
                                HttpServletRequest request){

        String baseMediaPath = File.separator + Paths.get("media/centre") + File.separator;

        Page<Album> pagedAlbums = albumService.getAlbumList(page, pageSize);
        List<Album> albums = pagedAlbums.getContent();

        model.addAttribute("activeDashPage", "albums-list");
        model.addAttribute("currentPage", pagedAlbums.getNumber()+1);
        model.addAttribute("totalItems", pagedAlbums.getTotalElements());
        model.addAttribute("totalPages", pagedAlbums.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("albums", albums);
        model.addAttribute("baseMediaPath", baseMediaPath);

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/form")
    public String showNewAlbumForm(Model model){

        model.addAttribute("media", new Media());
        model.addAttribute("activeDashPage", "media-form");

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/processForm")
    public String processAlbumForm(@ModelAttribute Album album, Model model){

        Album savedAlbum = albumService.save(album);

        model.addAttribute("album", new Album());
        model.addAttribute("activeDashPage", "album-form");

        return DASHBOARD_MAIN_PANEL;
    }
}
