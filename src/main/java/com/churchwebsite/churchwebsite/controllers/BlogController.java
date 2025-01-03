package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.Album;
import com.churchwebsite.churchwebsite.entities.Blog;
import com.churchwebsite.churchwebsite.entities.BlogCategory;
import com.churchwebsite.churchwebsite.entities.Settings;
import com.churchwebsite.churchwebsite.services.*;
import com.churchwebsite.churchwebsite.utils.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("dashboard/blogs")
public class BlogController {
    private final BlogService blogService;
    private final BlogCategoryService blogCategoryService;
    private final SettingsService settingsService;
    private final UserService userService;
    private final AlbumService albumService;
    private final PaginationService paginationService;

    @Value("${settings.default.page.size:10}")
    private int defaultPageSize;

    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";

    @Autowired
    public BlogController(BlogService blogService,
                          BlogCategoryService blogCategoryService,
                          SettingsService settingsService,
                          AlbumService albumService,
                          UserService userService,
                          PaginationService paginationService) {
        this.blogService = blogService;
        this.blogCategoryService = blogCategoryService;
        this.settingsService = settingsService;
        this.albumService = albumService;
        this.userService = userService;
        this.paginationService = paginationService;
    }


    @GetMapping("/form")
    public String showNewBlogForm(Model model){

        List<BlogCategory> blogCategories = blogCategoryService.findAll();

        // Blog album for blog related medias
        Album album = albumService.findAlbumByAlbumName("Blog");

        model.addAttribute("blog", new Blog());
        model.addAttribute("blogCategories", blogCategories);
        model.addAttribute("activeDashPage", "blog-form");
        model.addAttribute("album", album);

        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/processBlogForm")
    public String processBlogForm(@ModelAttribute Blog blog, Model model){

        CustomUserDetails userDetails = userService.getCurrentUser();

        // Set active and archive status of blog if new/existing object
        if(blog.getBlogId() == 0){
            blog.setActive(true);
            blog.setArchived(false);
        }else{
            blog.setBlogTime(blog.getBlogTime());
        }


        blog.setUserId(userDetails.getUser());

        Blog savedBlog = blogService.save(blog);

        return "redirect:/dashboard/blogs";
    }

    @GetMapping("")
    public String showBlogsList( @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                 @RequestParam(value = "size", required = false) Integer pageSize,
                                 @RequestParam(value = "blogCatId", required = false, defaultValue = "0") Integer blogCatId,
                                 HttpServletRequest request,
                                    Model model){

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<Blog> pagedBlog = blogService.findAll(page, pageSize, blogCatId);
        List<Blog> blogs = pagedBlog.getContent();

        List<BlogCategory> blogCategories = blogCategoryService.findAll();

        model.addAttribute("activeDashPage", "blogs-list");
        model.addAttribute("blogs", blogs);
        model.addAttribute("currentPage", pagedBlog.getNumber()+1);
        model.addAttribute("totalItems", pagedBlog.getTotalElements());
        model.addAttribute("totalPages", pagedBlog.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("blogCategories", blogCategories);
        model.addAttribute("blogCatId", blogCatId);


        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/detail/{id}")
    public String showBlogCategoryDetail(@PathVariable(value = "id", required = true) Integer blogId,
                                         Model model){

        Blog blog = blogService.findById(blogId);
        model.addAttribute("activeDashPage", "blog-detail");
        model.addAttribute("blog", blog);

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/edit/{id}")
    public String showBlogCategoryFormForUpdate(@PathVariable(value = "id", required = true) Integer blogId,
                                                Model model){

        List<BlogCategory> blogCategories = blogCategoryService.findAll();
        Blog blog = blogService.findById(blogId);

        Album album = albumService.findAlbumByAlbumName("Blog");

        model.addAttribute("activeDashPage", "blog-form");
        model.addAttribute("blog", blog);
        model.addAttribute("blogCategories", blogCategories);
        model.addAttribute("album", album);


        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/delete/{id}")
    public String deleteASingleBlog(@PathVariable(value = "id", required = true) int blogId){
        blogService.deleteById(blogId);

        return "redirect:/dashboard/blogs";
    }

}
