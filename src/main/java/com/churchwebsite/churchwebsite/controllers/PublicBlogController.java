package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.Blog;
import com.churchwebsite.churchwebsite.entities.BlogCategory;
import com.churchwebsite.churchwebsite.services.BlogCategoryService;
import com.churchwebsite.churchwebsite.services.BlogService;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.PaginationService;
import jakarta.servlet.http.HttpServletRequest;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("blogs")
public class PublicBlogController {
    private final BlogService blogService;
    private final BlogCategoryService blogCategoryService;
    private final PaginationService paginationService;
    private final ChurchDetailService churchDetailService;

    @Value("${settings.default.page.size:10}")
    private int defaultPageSize;

    private final String PUBLIC_CONTENT = "layouts/base";

    @Autowired
    public PublicBlogController(BlogService blogService,
                                PaginationService paginationService,
                                ChurchDetailService churchDetailService,
                                BlogCategoryService blogCategoryService
                        ) {
        this.blogService = blogService;
        this.blogCategoryService = blogCategoryService;
        this.paginationService = paginationService;
        this.churchDetailService = churchDetailService;

    }

    @GetMapping("")
    public String showBlogsList( @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                 @RequestParam(value = "size", required = false) Integer pageSize,
                                 @RequestParam(value = "blogCatId", required = false, defaultValue = "0") Integer blogCatId,
                                 @RequestParam(value = "archived", required = false, defaultValue = "false") boolean archived,
                                 @RequestParam(value = "active", required = false, defaultValue = "true") boolean active,
                                HttpServletRequest request,
                                Model model){

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<Blog> pagedBlog  = blogService.findBlogsByArchiveAndActive(page, pageSize, blogCatId, archived, active);
        List<Blog> blogs = pagedBlog.getContent();

        blogs.forEach(blog->{
            String excerpt = generateExcerpt(blog.getBlogText(), 200);
            blog.setExcerpt(excerpt);
        });

        List<BlogCategory> blogCategories = blogCategoryService.findAll();

        model.addAttribute("activeContentPage", "blogs-list");
        model.addAttribute("blogs", blogs);
        model.addAttribute("blogCategories", blogCategories);
        model.addAttribute("currentPage", pagedBlog.getNumber()+1);
        model.addAttribute("totalItems", pagedBlog.getTotalElements());
        model.addAttribute("totalPages", pagedBlog.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("blogCatId", blogCatId);
        model.addAttribute("pageTitle", "Blogs List");

        return PUBLIC_CONTENT;
    }

    private String generateExcerpt(String blogText, int length) {
        if(blogText == null || blogText.isEmpty()){
            return "";
        }else{
            String plainText = Jsoup.parse(blogText).text();

            return plainText.length() < length ? plainText : plainText.substring(0, length) + "...";
        }
    }

    @GetMapping("/detail/{id}")
    public String showBlogCategoryDetail(@PathVariable(value = "id", required = true) Integer blogId,
                                         Model model){

        Blog blog = blogService.findById(blogId);
        model.addAttribute("activeContentPage", "blog-detail");
        model.addAttribute("blog", blog);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Blog Detail");


        return PUBLIC_CONTENT;
    }

}
