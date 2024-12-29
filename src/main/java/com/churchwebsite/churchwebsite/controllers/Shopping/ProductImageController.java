package com.churchwebsite.churchwebsite.controllers.Shopping;

import com.churchwebsite.churchwebsite.entities.shopping.Product;
import com.churchwebsite.churchwebsite.entities.shopping.ProductImage;
import com.churchwebsite.churchwebsite.enums.ImageType;
import com.churchwebsite.churchwebsite.services.PaginationService;
import com.churchwebsite.churchwebsite.services.shopping.ProductImageService;
import com.churchwebsite.churchwebsite.services.shopping.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Controller
@RequestMapping("dashboard/products/images")
public class ProductImageController {


    private final ProductService productService;
    private final PaginationService paginationService;
    private final ProductImageService productImageService;

    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";

    @Autowired
    public ProductImageController(ProductService productService, PaginationService paginationService, ProductImageService productImageService) {
        this.productService = productService;
        this.paginationService = paginationService;
        this.productImageService = productImageService;
    }

    @GetMapping("/{prodId}")
    public String getProductForImages(Model model,
                               @PathVariable("prodId") Integer prodId,
//                               @RequestParam(value = "page", required = false, defaultValue = "1") int page,
//                               @RequestParam(value = "size", required = false) Integer pageSize,
//                               @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                               HttpServletRequest request){


        Product product = productService.getProductById(prodId).orElse(null);

        model.addAttribute("activeDashPage", "product-images-list");
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        model.addAttribute("imageTypes", ImageType.values());


        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/detail/{id}")
    public String getImageById(@PathVariable Integer id, Model model) {
        model.addAttribute("image", productImageService.getImageById(id).orElse(null));
        model.addAttribute("activeDashPage", "product-image-detail");

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/form/{productId}")
    public String showImageForm(@PathVariable Integer productId, Model model) {
        model.addAttribute("image", new ProductImage());
        model.addAttribute("productId", productId);
        model.addAttribute("activeDashPage", "product-image-form");
        model.addAttribute("imageTypes", ImageType.values());

        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/form/process")
    public String createImage(@ModelAttribute ProductImage productImage,
                              @RequestParam("productId") Integer productId,
                              @RequestParam("imageFile") MultipartFile multipartFile) {

        productImageService.saveImage(productImage, productId, multipartFile);

        return "redirect:/dashboard/products/detail/"+productId;
    }

//    @PutMapping("/edit/{id}")
//    public String updateImage(@PathVariable Integer id, @ModelAttribute ProductImage updatedImage) {
//        productImageService.updateImage(id, updatedImage);
//        return "redirect:/product-images";
//    }

    @GetMapping("/delete/{id}")
    public String deleteImage(@PathVariable Integer id) {
        ProductImage image = productImageService.getImageById(id).orElse(null);
        productImageService.deleteImage(id);
        return "redirect:/dashboard/products/detail/"+ Objects.requireNonNull(image).getProduct().getProductId();
    }
}

