package com.eCommerce.controller;

import com.eCommerce.entity.User;
import com.eCommerce.service.CategoryService;
import com.eCommerce.service.ProductService;
import com.eCommerce.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private CategoryService categoryService;
    private ProductService productService;
    private UserService userService;

    public UserController(CategoryService categoryService, ProductService productService, UserService userService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.userService = userService;
    }

    @ModelAttribute
    public void getUserDetails(Principal p, Model m) {
        User user = null;
        if (p != null) {
            user = userService.getUserByEmail(p.getName());
        }
        if (user == null) {
            user = new User();
            user.setName("Guest");
        }
        m.addAttribute("user", user);
    }

    @GetMapping("/home")
    public String home() {
        return "user/home"; // Returns the view name "home"
    }
}
