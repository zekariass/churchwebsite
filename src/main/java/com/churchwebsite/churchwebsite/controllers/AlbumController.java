package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.Album;
import com.churchwebsite.churchwebsite.entities.Image;
import com.churchwebsite.churchwebsite.services.AlbumService;
import com.churchwebsite.churchwebsite.services.PaginationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("images/albums")
public class AlbumController {

    @Autowired
    private  ResourceLoader resourceLoader;


    private final AlbumService albumService;
    private final PaginationService paginationService;

    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";

    @Autowired
    public AlbumController(AlbumService albumService, PaginationService paginationService) {
        this.albumService = albumService;
        this.paginationService = paginationService;
    }

    @GetMapping("")
    public String showAlbumList(Model model,
                                @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                @RequestParam(value = "size", required = false) Integer pageSize,
                                HttpServletRequest request){

        String baseImagePath = File.separator + Paths.get("image/centre") + File.separator;

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<Album> pagedAlbums = albumService.getAlbumList(page, pageSize, Sort.by(Sort.Order.desc("creationTime")));
        List<Album> albums = pagedAlbums.getContent();

        model.addAttribute("activeDashPage", "albums-list");
        model.addAttribute("currentPage", pagedAlbums.getNumber() + 1);
        model.addAttribute("totalItems", pagedAlbums.getTotalElements());
        model.addAttribute("totalPages", pagedAlbums.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("albums", albums);
        model.addAttribute("baseImagePath", baseImagePath);

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/form")
    public String showNewAlbumForm(Model model){

        model.addAttribute("album", new Album());
        model.addAttribute("activeDashPage", "album-form");

        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/processForm")
    public String processAlbumForm(@ModelAttribute Album album, Model model){

        Album savedAlbum = albumService.save(album);

        model.addAttribute("album", new Album());
        model.addAttribute("activeDashPage", "album-form");

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/detail/{id}")
    public String showAlbumDetail(@PathVariable(value = "id", required = false) int albumId, Model model){

        Album album = albumService.getAlbumById(albumId);
        String baseImagePath = File.separator + Paths.get("image/centre") + File.separator;

        model.addAttribute("album", album);
        model.addAttribute("activeDashPage", "album-detail");
        model.addAttribute("baseImagePath", baseImagePath);

        return DASHBOARD_MAIN_PANEL;
    }
}
