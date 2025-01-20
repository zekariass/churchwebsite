package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.Album;
import com.churchwebsite.churchwebsite.services.AlbumService;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.PaginationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
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

    @Value("${file.storage.type}")
    private String fileStorageType;

    private final AlbumService albumService;
    private final PaginationService paginationService;
    private final ChurchDetailService churchDetailService;



//    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";
    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-layouts/dash-base";

    @Autowired
    public AlbumController(AlbumService albumService, PaginationService paginationService, ChurchDetailService churchDetailService) {
        this.albumService = albumService;
        this.paginationService = paginationService;
        this.churchDetailService = churchDetailService;
    }

    @GetMapping("")
    public String showAlbumList(Model model,
                                @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                @RequestParam(value = "size", required = false) Integer pageSize,
                                @RequestParam(value = "sortBy", required = false, defaultValue = "creationTime") String sortBy,
                                HttpServletRequest request){

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<Album> pagedAlbums = albumService.getAlbumList(page, pageSize, sortBy);
        List<Album> albums = pagedAlbums.getContent();

        if(fileStorageType.equalsIgnoreCase("local")) {
            String baseImagePath = File.separator + Paths.get("image/centre") + File.separator;
            model.addAttribute("baseImagePath", baseImagePath);
        }

        model.addAttribute("activeDashPage", "albums-list");
        model.addAttribute("currentPage", pagedAlbums.getNumber() + 1);
        model.addAttribute("totalItems", pagedAlbums.getTotalElements());
        model.addAttribute("totalPages", pagedAlbums.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("albums", albums);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Albums list");

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/list")
    public String showAlbumListTabular(Model model,
                                @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                @RequestParam(value = "size", required = false) Integer pageSize,
                                @RequestParam(value = "sortBy", required = false, defaultValue = "creationTime") String sortBy,
                                HttpServletRequest request){

        if(fileStorageType.equalsIgnoreCase("local")) {
            String baseImagePath = File.separator + Paths.get("image/centre") + File.separator;
            model.addAttribute("baseImagePath", baseImagePath);
        }

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<Album> pagedAlbums = albumService.getAlbumList(page, pageSize, sortBy);
        List<Album> albums = pagedAlbums.getContent();

        model.addAttribute("activeDashPage", "albums-list-tabular");
        model.addAttribute("currentPage", pagedAlbums.getNumber() + 1);
        model.addAttribute("totalItems", pagedAlbums.getTotalElements());
        model.addAttribute("totalPages", pagedAlbums.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("albums", albums);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Albums list");

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/form")
    public String showNewAlbumForm(Model model){

        model.addAttribute("album", new Album());
        model.addAttribute("activeDashPage", "album-form");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Album Form");

        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/processForm")
    public String processAlbumForm(@ModelAttribute Album album, Model model){

        Album savedAlbum = albumService.save(album);

        model.addAttribute("album", new Album());
        model.addAttribute("activeDashPage", "album-form");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());


        return "redirect:/images/albums/list";
    }

    @GetMapping("/detail/{id}")
    public String showAlbumDetail(@PathVariable(value = "id", required = false) int albumId, Model model){

        Album album = albumService.getAlbumById(albumId);
        if(fileStorageType.equalsIgnoreCase("local")) {
            String baseImagePath = File.separator + Paths.get("image/centre") + File.separator;
            model.addAttribute("baseImagePath", baseImagePath);
        }

        model.addAttribute("album", album);
        model.addAttribute("activeDashPage", "album-detail");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Album Detail");


        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/edit/{id}")
    public String updateAlbum(@PathVariable(value = "id", required = false) int albumId, Model model){

        Album album = albumService.getAlbumById(albumId);

        model.addAttribute("album", album);
        model.addAttribute("activeDashPage", "album-form");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Album Form");

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/delete/{id}")
    public String albumDelete(@PathVariable(value = "id", required = false) int albumId, Model model){

        albumService.deleteById(albumId);

        return "redirect:/images/albums/list";
    }


}
