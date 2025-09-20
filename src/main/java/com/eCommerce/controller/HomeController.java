package com.eCommerce.controller;

import com.eCommerce.entity.User;
import com.eCommerce.service.CategoryService;
import com.eCommerce.service.ProductService;
import com.eCommerce.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;

@Controller
public class HomeController {

    private CategoryService categoryService;
    private ProductService productService;
    private UserService userService;

    public HomeController(CategoryService categoryService, ProductService productService, UserService userService) {
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


    @GetMapping("/")
    public String home() {
        return "home"; // Returns the view name "home"
    }

    @GetMapping("/signin")
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


    // Displays the product details page for a specific product by its ID
    @GetMapping("/viewDetails/{id}")
    public String product(@PathVariable int id, Model m) {
        m.addAttribute("product", productService.getProductById(id)); // Adds the product details to the model
        return "viewProduct"; // Returns the view name "viewProduct"
    }


    // Handles user registration and saves the user details along with the profile image
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user, @RequestParam("file") MultipartFile file, HttpSession session) throws IOException {

        if (userService.existsByPhone(user.getPhone())) {
            session.setAttribute("errorMsg", "User with this phone number already exists!");
            return "redirect:/register";
        }

        String imageName = file!=null ? file.getOriginalFilename() : "No image";
        user.setProfileImage(imageName); // Set the profile image name


            User savedUser = userService.saveUser(user); // Save the user details

            if(savedUser==null){
                session.setAttribute("errorMsg", "Something went wrong!! User not added.");
            }
            else{

                File saveFile = new ClassPathResource("static/img/profile_img").getFile();
                File file1 = new File(saveFile.getAbsolutePath()+File.separator+imageName);
                file.transferTo(file1); // Save the profile image to the specified directory

                session.setAttribute("successMsg", "User added successfully.");
            }

        return "redirect:/register"; // Redirects to the register page

    }
}
