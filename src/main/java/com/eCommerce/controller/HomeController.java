package com.eCommerce.controller;

import com.eCommerce.service.CategoryService;
import com.eCommerce.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private CategoryService categoryService;
    private ProductService productService;

    public HomeController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/")
    public String home() {
        return "home"; // Returns the view name "home"
    }

    @GetMapping("/login")
    public String login(){
        return "login"; // Returns the view name "login"
    }

    @GetMapping("/register")
    public String register(){
        return "register"; // Returns the view name "register"
    }


    // Displays the products page with active categories and products
    @GetMapping("/products")
    public String products(Model m){

        m.addAttribute("categories", categoryService.getAllActiveCategories()); // Adds active categories to the model
        m.addAttribute("products", productService.getAllActiveProducts()); // Adds active products to the model
        return "products"; // Returns the view name "products"
    }

    @GetMapping("/viewDetails")
    public String product(){
        return "viewProduct"; // Returns the view name "viewProduct"
    }
}
