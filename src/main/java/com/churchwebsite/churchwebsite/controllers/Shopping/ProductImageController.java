package com.churchwebsite.churchwebsite.controllers.Shopping;

import com.churchwebsite.churchwebsite.entities.shopping.Product;
import com.churchwebsite.churchwebsite.entities.shopping.ProductImage;
import com.churchwebsite.churchwebsite.enums.ImageType;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
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
    private final ProductImageService productImageService;
    private  final ChurchDetailService churchDetailService;

//    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";
    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-layouts/dash-base";

    @Autowired
    public ProductImageController(ProductService productService, ProductImageService productImageService, ChurchDetailService churchDetailService) {
        this.productService = productService;
        this.productImageService = productImageService;
        this.churchDetailService = churchDetailService;
    }

    @GetMapping("/{prodId}")
    public String getProductForImages(Model model,
                               @PathVariable("prodId") Integer prodId,
                               HttpServletRequest request){


        Product product = productService.getProductById(prodId).orElse(null);

        model.addAttribute("activeDashPage", "product-images-list");
        model.addAttribute("product", product);
        assert product != null;
        model.addAttribute("images", product.getImages());
        model.addAttribute("imageTypes", ImageType.values());
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Product Images");

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/detail/{id}")
    public String getImageById(@PathVariable Integer id, Model model) {
        model.addAttribute("image", productImageService.getImageById(id).orElse(null));
        model.addAttribute("activeDashPage", "product-image-detail");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Product Image");


        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/form/{productId}")
    public String showImageForm(@PathVariable Integer productId, Model model) {
        model.addAttribute("image", new ProductImage());
        model.addAttribute("productId", productId);
        model.addAttribute("activeDashPage", "product-image-form");
        model.addAttribute("imageTypes", ImageType.values());
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Product Image Form");

        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/form/process")
    public String createImage(@ModelAttribute ProductImage productImage,
                              @RequestParam("productId") Integer productId,
                              @RequestParam("imageFile") MultipartFile multipartFile) {

        productImageService.saveImage(productImage, productId, multipartFile);

        return "redirect:/dashboard/products/detail/"+productId;
    }

    @GetMapping("/delete/{id}")
    public String deleteImage(@PathVariable Integer id) {
        ProductImage image = productImageService.getImageById(id).orElse(null);
        productImageService.deleteImage(id);
        return "redirect:/dashboard/products/detail/"+ Objects.requireNonNull(image).getProduct().getProductId();
    }
}

