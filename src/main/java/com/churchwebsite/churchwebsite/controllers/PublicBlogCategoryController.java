package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.dtos.ChurchDetailDTO;
import com.churchwebsite.churchwebsite.entities.BlogCategory;
import com.churchwebsite.churchwebsite.services.BlogCategoryService;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.PaginationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("blogs/categories")
public class PublicBlogCategoryController {

    private final BlogCategoryService blogCategoryService;
    private final PaginationService paginationService;
    private final ChurchDetailDTO churchDetail;

    private final String PUBLIC_CONTENT = "layouts/base";

    @Value("${settings.default.page.size:10}")
    private int defaultPageSize;

    @Autowired
    public PublicBlogCategoryController(BlogCategoryService blogCategoryService,
                                        ChurchDetailService churchDetailService,
                                        PaginationService paginationService) {
        this.blogCategoryService = blogCategoryService;
        this.paginationService = paginationService;
        this.churchDetail = churchDetailService.getChurchDetail();
    }

    @GetMapping("")
    public String showBlogCategoryList(Model model,
                                       @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                       @RequestParam(value = "size", required = false) Integer pageSize,
                                       HttpServletRequest request){

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<BlogCategory> pagedBlogCategories = blogCategoryService.findAll(page, pageSize);
        List<BlogCategory> blogCategories = pagedBlogCategories.getContent();

        model.addAttribute("activeContentPage", "blog-category-list");
        model.addAttribute("blogCategories", blogCategories);
        model.addAttribute("currentPage", pagedBlogCategories.getNumber()+1);
        model.addAttribute("totalItems", pagedBlogCategories.getTotalElements());
        model.addAttribute("totalPages", pagedBlogCategories.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("churchDetail", churchDetail);

        return PUBLIC_CONTENT;
    }

    @GetMapping("/detail/{id}")
    public String showBlogCategoryDetail(@PathVariable(value = "id", required = true) Integer catId,
                                         Model model){

        BlogCategory blogCategory = blogCategoryService.findById(catId);
        model.addAttribute("activeContentPage", "blog-category-detail");
        model.addAttribute("blogCategory", blogCategory);
        model.addAttribute("churchDetail", churchDetail);

        return PUBLIC_CONTENT;
    }
}
