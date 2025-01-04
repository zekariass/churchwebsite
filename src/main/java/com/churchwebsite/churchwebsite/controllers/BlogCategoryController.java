package com.churchwebsite.churchwebsite.controllers;

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
@RequestMapping("/dashboard/blogs/categories")
public class BlogCategoryController {

    private final BlogCategoryService blogCategoryService;
    private final PaginationService paginationService;
    private final ChurchDetailService churchDetailService;

    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";

    @Value("${settings.default.page.size:10}")
    private int defaultPageSize;

    @Autowired
    public BlogCategoryController(BlogCategoryService blogCategoryService, PaginationService paginationService, ChurchDetailService churchDetailService) {
        this.blogCategoryService = blogCategoryService;
        this.paginationService = paginationService;
        this.churchDetailService = churchDetailService;
    }

    @GetMapping("/addBlogCategoryForm")
    public String showBlogCategoryForm(Model model){

        model.addAttribute("blogCategory", new BlogCategory());
        model.addAttribute("activeDashPage", "blog-category-form");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());

        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/processBlogCategoryForm")
    public String processBloCategoryForm(@ModelAttribute BlogCategory blogCategory,
                                         @RequestParam(value = "blogCategoryId", required = false) int blogCategoryId,
//                                         @RequestParam(value = "blogCategoryDescription", required = false) String blogCategoryDescription,
                                         Model model){

//        blogCategory.setBlogCategoryDescription(blogCategoryDescription);
        BlogCategory savedBlogCategory = blogCategoryService.save(blogCategory);

        model.addAttribute("activeDashPage", "blog-category-list");

        if(blogCategoryId != 0){
            return "redirect:/dashboard/blogs/categories/detail/"+blogCategoryId;
        }
        return "redirect:/dashboard/blogs/categories";
    }

    @GetMapping("")
    public String showBlogCategoryList(Model model,
                                       @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                       @RequestParam(value = "size", required = false) Integer pageSize,
                                       HttpServletRequest request){

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();


        Page<BlogCategory> pagedBlogCategories = blogCategoryService.findAll(page, pageSize);
        List<BlogCategory> blogCategories = pagedBlogCategories.getContent();

        model.addAttribute("activeDashPage", "blog-category-list");
        model.addAttribute("blogCategories", blogCategories);
        model.addAttribute("currentPage", pagedBlogCategories.getNumber()+1);
        model.addAttribute("totalItems", pagedBlogCategories.getTotalElements());
        model.addAttribute("totalPages", pagedBlogCategories.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());


        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/detail/{id}")
    public String showBlogCategoryDetail(@PathVariable(value = "id", required = true) Integer catId,
                                         Model model){

        BlogCategory blogCategory = blogCategoryService.findById(catId);
        model.addAttribute("activeDashPage", "blog-category-detail");
        model.addAttribute("blogCategory", blogCategory);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());


        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/edit/{id}")
    public String showBlogCategoryFormForUpdate(@PathVariable(value = "id", required = true) Integer catId,
                                         Model model){

        BlogCategory blogCategory = blogCategoryService.findById(catId);
        model.addAttribute("activeDashPage", "blog-category-form");
        model.addAttribute("blogCategory", blogCategory);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/delete/{id}")
    public  String deleteASingleBlog(@PathVariable(value = "id", required = true) int blogCatId){
        blogCategoryService.deleteById(blogCatId);

        return "redirect:/dashboard/blogs/categories";
    }
}
