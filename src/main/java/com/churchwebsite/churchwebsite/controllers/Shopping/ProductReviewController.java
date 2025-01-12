package com.churchwebsite.churchwebsite.controllers.Shopping;

import com.churchwebsite.churchwebsite.services.shopping.ProductReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reviews")
public class ProductReviewController {

    @Autowired
    private ProductReviewService productReviewService;


}

