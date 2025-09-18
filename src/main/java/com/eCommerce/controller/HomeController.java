package com.eCommerce.controller;

import com.eCommerce.service.CategoryService;
import com.eCommerce.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String products(Model m, @RequestParam(value = "category", required = false) String category) {

        m.addAttribute("categories", categoryService.getAllActiveCategories()); // Adds active categories to the model
        m.addAttribute("products", productService.getAllActiveProducts(category)); // Adds active products to the model
        m.addAttribute("paramValue", category); // Adds the category parameter to the model
        return "products"; // Returns the view name "products"
    }

    @GetMapping("/viewDetails/{id}")
    public String product(@PathVariable int id, Model m) {
        m.addAttribute("product", productService.getProductById(id)); // Adds the product details to the model
        return "viewProduct"; // Returns the view name "viewProduct"
    }
}
