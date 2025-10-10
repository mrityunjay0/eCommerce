package com.eCommerce.controller;

import com.eCommerce.entity.Category;
import com.eCommerce.entity.User;
import com.eCommerce.service.CartService;
import com.eCommerce.service.CategoryService;
import com.eCommerce.service.ProductService;
import com.eCommerce.service.UserService;
import com.eCommerce.utils.CommonUtils;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
public class HomeController {

    private CategoryService categoryService;
    private ProductService productService;
    private UserService userService;
    private CommonUtils commonUtils;
    private PasswordEncoder passwordEncoder;
    private CartService cartService;

    public HomeController(CategoryService categoryService, ProductService productService, UserService userService,
                          CommonUtils commonUtils, PasswordEncoder passwordEncoder, CartService cartService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.userService = userService;
        this.commonUtils = commonUtils;
        this.passwordEncoder = passwordEncoder;
        this.cartService = cartService;
    }

    @ModelAttribute
    public void getUserDetails(Principal p, Model m) {

        User user = null;

        if (p != null) {
            user = userService.getUserByEmail(p.getName());
            if (user != null) {
                m.addAttribute("user", user);
                Integer cartCount = cartService.getCartCount(user.getId());
                m.addAttribute("cartCount", cartCount);
            } else {
                // principal present but user record not found (first run, empty DB, etc.)
                m.addAttribute("cartCount", 0);
            }
        } else {
            // not logged in
            m.addAttribute("cartCount", 0);
        }

        List<Category> categories = categoryService.getAllActiveCategories();
        m.addAttribute("categories", categories);
    }


    // Displays the home page
    @GetMapping("/")
    public String home() {
        return "home"; // Returns the view name "home"
    }

    // Handles the login page request
    @GetMapping("/signin")
    public String login(){
        return "login"; // Returns the view name "login"
    }

    // Handles the registration page request
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


    // Handles the forgot password request
    @GetMapping("/forgotPassword")
    public String forgotPassword(){
        return "forgotPassword"; // Redirects to the forgotPassword page
    }


    // Handles the forgot password logic
    @PostMapping("/forgotPassword")
    public String processForgotPassword(@RequestParam String email, HttpSession session, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {

        User user = userService.getUserByEmail(email);
        if(user==null){
            session.setAttribute("errorMsg","Invalid email.");
        }
        else{

            String resetToken = UUID.randomUUID().toString();
            userService.updateResetToken(email,resetToken);

            String url = CommonUtils.generateUrl(request) + "/resetPassword?token=" + resetToken;

            boolean sendMail = commonUtils.sendMail(url,email);
            if(sendMail){
                session.setAttribute("successMsg","Reset link sent.");
            }
            else {
                session.setAttribute("errorMsg","Error occurred, link not sent.");
            }
        }

        return "redirect:/forgotPassword";
    }


    // Handles the reset password request
    @GetMapping("/resetPassword")
    public String resetPassword(@RequestParam(required = false) String token, Model m) {
        if (token == null) {
            m.addAttribute("errorMsg", "Missing reset token. Please use the link sent to your email.");
            return "errorMessage";
        }
        User user = userService.getUserByResetToken(token);
        if (user == null) {
            m.addAttribute("errorMsg","Invalid or expired link.");
            return "errorMessage";
        }
        m.addAttribute("token", token);
        return "resetPassword";
    }


    // Excute the reset password request
    @PostMapping("/resetPasswordExecute")
    public String resetPasswordExecute(@RequestParam String token, @RequestParam String password,
                                       @RequestParam String confirmPassword, HttpSession session, Model m) {

        if (!password.equals(confirmPassword)) {
            m.addAttribute("errorMsg", "Passwords do not match.");
            m.addAttribute("token", token);           // keep token so the form can post again
            return "resetPassword";
        }

        User user = userService.getUserByResetToken(token);
        if (user == null) {
            m.addAttribute("errorMsg", "Invalid or expired link.");
            return "errorMessage";
        }

        user.setPassword(passwordEncoder.encode(password));
        user.setResetToken(null);
        userService.updateUserPassword(user);

        session.setAttribute("successMsg","Password changed successfully");
        return "redirect:/signin"; // or Option B with token
    }
}
