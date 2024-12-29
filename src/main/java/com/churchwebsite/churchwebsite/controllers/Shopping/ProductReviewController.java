package com.churchwebsite.churchwebsite.controllers.Shopping;

import com.churchwebsite.churchwebsite.entities.shopping.ProductReview;
import com.churchwebsite.churchwebsite.services.shopping.ProductReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ProductReviewController {

    @Autowired
    private ProductReviewService productReviewService;

    @GetMapping
    public String getAllReviews(Model model) {
        List<ProductReview> reviews = productReviewService.getAllReviews();
        model.addAttribute("reviews", reviews);
        return "review/list";
    }

    @GetMapping("/{id}")
    public String getReviewById(@PathVariable Integer id, Model model) {
        model.addAttribute("review", productReviewService.getReviewById(id).orElse(null));
        return "review/detail";
    }

    @PostMapping
    public String createReview(@ModelAttribute ProductReview productReview) {
        productReviewService.saveReview(productReview);
        return "redirect:/reviews";
    }

    @PutMapping("/{id}")
    public String updateReview(@PathVariable Integer id, @ModelAttribute ProductReview updatedReview) {
        productReviewService.updateReview(id, updatedReview);
        return "redirect:/reviews";
    }

    @DeleteMapping("/{id}")
    public String deleteReview(@PathVariable Integer id) {
        productReviewService.deleteReview(id);
        return "redirect:/reviews";
    }
}

