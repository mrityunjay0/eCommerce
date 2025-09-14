package com.eCommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/")
    public String adminIndex() {

        return "admin/index"; // Returns the view name "admin/index"
    }

    @GetMapping("/addProduct")
    public String addProduct(){

        return "admin/addProduct"; // Returns the view name "admin/addProduct"
    }

    @GetMapping("/addCategory")
    public String addCategory(){

        return "admin/addCategory"; // Returns the view name "admin/addCategory"
    }
}