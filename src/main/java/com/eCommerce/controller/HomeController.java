package com.eCommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

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

    @GetMapping("/products")
    public String products(){
        return "products"; // Returns the view name "products"
    }
}
