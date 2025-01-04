package com.churchwebsite.churchwebsite.controllers.Shopping;

import com.churchwebsite.churchwebsite.entities.shopping.Product;
import com.churchwebsite.churchwebsite.entities.shopping.ProductCategory;
import com.churchwebsite.churchwebsite.enums.ProductDeliveryType;
import com.churchwebsite.churchwebsite.enums.ProductListingStatus;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.PaginationService;
import com.churchwebsite.churchwebsite.services.shopping.ProductCategoryService;
import com.churchwebsite.churchwebsite.services.shopping.ProductService;
import com.churchwebsite.churchwebsite.utils.LocaleUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dashboard/products")
public class ProductController {

    private final ProductService productService;
    private final PaginationService paginationService;
    private final ProductCategoryService productCategoryService;
    private final LocaleUtil localeUtil;
    private  final ChurchDetailService churchDetailService;

    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";

    public ProductController(ProductService productService,
                             ProductCategoryService productCategoryService,
                             PaginationService paginationService,
                             LocaleUtil localeUtil, ChurchDetailService churchDetailService) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
        this.paginationService = paginationService;
        this.localeUtil = localeUtil;
        this.churchDetailService = churchDetailService;
    }

    @GetMapping
    public String getAllProducts(Model model,
                                 @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                 @RequestParam(value = "size", required = false) Integer pageSize,
                                 @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
                                 HttpServletRequest request){

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<Product> pagedProducts = productService.getAllProducts(page, pageSize, sortBy);
        List<Product> products = pagedProducts.getContent();

        model.addAttribute("activeDashPage", "products-list");
        model.addAttribute("products", products);
        model.addAttribute("currencySymbol", localeUtil.getCurrency().getSymbol());

        model.addAttribute("currentPage", pagedProducts.getNumber()+1);
        model.addAttribute("totalItems", pagedProducts.getTotalElements());
        model.addAttribute("totalPages", pagedProducts.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/detail/{id}")
    public String getProductById(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.getProductById(id).orElse(null));
        model.addAttribute("activeDashPage", "product-detail");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/form")
    public String showProductForm(Model model) {

        List<ProductCategory> categories = productCategoryService.getAllCategories();

        model.addAttribute("product", new Product());
        model.addAttribute("categories", categories);
        model.addAttribute("currencySymbol", localeUtil.getCurrency().getSymbol());
        model.addAttribute("deliveryTypes", ProductDeliveryType.values());
        model.addAttribute("listingStatuses", ProductListingStatus.values());
        model.addAttribute("activeDashPage", "product-form");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());

        return DASHBOARD_MAIN_PANEL;
    }


    @PostMapping("/form/process")
    public String createProduct(@ModelAttribute Product product) {
        productService.saveProduct(product);
        return "redirect:/dashboard/products";
    }

    @GetMapping("/edit/{id}")
    public String showProductEditForm(@PathVariable("id") Integer id, Model model) {

        List<ProductCategory> categories = productCategoryService.getAllCategories();

        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("categories", categories);
        model.addAttribute("deliveryTypes", ProductDeliveryType.values());
        model.addAttribute("listingStatuses", ProductListingStatus.values());
        model.addAttribute("activeDashPage", "product-form");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());

        return DASHBOARD_MAIN_PANEL;
    }

//    @PutMapping("/edit/process")
//    public String updateProduct(@PathVariable Integer id, @ModelAttribute Product updatedProduct) {
//        productService.updateProduct(id, updatedProduct);
//        return "redirect:/dashboard/products";
//    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return "redirect:/dashboard/products";
    }
}

