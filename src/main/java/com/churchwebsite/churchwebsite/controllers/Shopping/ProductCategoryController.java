package com.churchwebsite.churchwebsite.controllers.Shopping;

import com.churchwebsite.churchwebsite.entities.shopping.ProductCategory;
import com.churchwebsite.churchwebsite.services.PaginationService;
import com.churchwebsite.churchwebsite.services.shopping.ProductCategoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("dashboard/products/categories")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;
    private final PaginationService paginationService;

    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";

    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService,
                                     PaginationService paginationService) {
        this.productCategoryService = productCategoryService;
        this.paginationService = paginationService;
    }

    @GetMapping("")
    public String getAllCategories(Model model,
                                   @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                   @RequestParam(value = "size", required = false) Integer pageSize,
                                   @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
                                   HttpServletRequest request){

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<ProductCategory> pagedCategories = productCategoryService.getAllCategories(page, pageSize, sortBy);
        List<ProductCategory> categories = pagedCategories.getContent();

        model.addAttribute("activeDashPage", "product-categories-list");
        model.addAttribute("categories", categories);

        model.addAttribute("currentPage", pagedCategories.getNumber()+1);
        model.addAttribute("totalItems", pagedCategories.getTotalElements());
        model.addAttribute("totalPages", pagedCategories.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("sortBy", sortBy);

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/detail/{id}")
    public String getCategoryById(@PathVariable Integer id, Model model) {
        model.addAttribute("category", productCategoryService.getCategoryById(id).orElse(null));
        model.addAttribute("activeDashPage", "product-category-detail");

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/form")
    public String showCategoryForm(Model model) {

        List<ProductCategory> categories = productCategoryService.getAllCategories();

        model.addAttribute("category", new ProductCategory());
        model.addAttribute("categories", categories);
        model.addAttribute("activeDashPage", "product-category-form");
        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/form/process")
    public String createCategory(@ModelAttribute ProductCategory productCategory) {
        productCategoryService.saveCategory(productCategory);
        return "redirect:/dashboard/products/categories";
    }

    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable("id") int categoryId, Model model) {

        List<ProductCategory> categories = productCategoryService.getAllCategories();

        model.addAttribute("categories", categories);
        model.addAttribute("category", productCategoryService.getCategoryById(categoryId).orElse(null));
        model.addAttribute("activeDashPage", "product-category-form");
        return DASHBOARD_MAIN_PANEL;
    }

//    @PutMapping("/edit/{id}")
//    public String updateCategory(@PathVariable Integer id, @ModelAttribute ProductCategory updatedCategory) {
//        productCategoryService.updateCategory(id, updatedCategory);
//        return "redirect:/dashboard/products/categories/detail/"+id;
//    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Integer id) {
        productCategoryService.deleteCategory(id);
        return "redirect:/dashboard/products/categories";
    }
}

