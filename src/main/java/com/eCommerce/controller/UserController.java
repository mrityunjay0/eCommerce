package com.eCommerce.controller;

import com.eCommerce.entity.Cart;
import com.eCommerce.entity.Category;
import com.eCommerce.entity.User;
import com.eCommerce.service.CartService;
import com.eCommerce.service.CategoryService;
import com.eCommerce.service.ProductService;
import com.eCommerce.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private CategoryService categoryService;
    private ProductService productService;
    private UserService userService;
    private CartService cartService;

    public UserController(CategoryService categoryService, ProductService productService, UserService userService, CartService cartService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.userService = userService;
        this.cartService = cartService;
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
        Integer cartCount = cartService.getCartCount(user.getId());
        m.addAttribute("cartCount", cartCount);

        List<Category> categories = categoryService.getAllActiveCategories();
        m.addAttribute("categories", categories);
    }

    @GetMapping("/home")
    public String home() {
        return "home"; // Returns the view name "home"
    }


    @GetMapping("/addCart")
    public String addToCart(@RequestParam Integer pid, @RequestParam Integer uid, HttpSession session){

        Cart saveCart = cartService.saveCart(pid,uid);
        if(saveCart == null){
            session.setAttribute("errorMsg","Failed to add cart, try again!!!");
        }
        else{
            session.setAttribute("successMsg","Added to cart!!!");
        }
        return "redirect:/viewDetails/" + pid;
    }
}
