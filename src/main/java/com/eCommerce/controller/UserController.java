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
import java.util.Collections;
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


    @GetMapping("/cart")
    public String cartPage(Principal p, Model m) {

        User user = getLoggedInUserDetails(p);

        if (user == null) {
            // Not logged in → show empty cart gracefully
            m.addAttribute("carts", Collections.emptyList());
            m.addAttribute("totalOrderPrice", 0.0);
            m.addAttribute("isEmpty", true);
            return "/user/cart";
        }

        List<Cart> carts = cartService.getCartByUser(user.getId());
        if (carts == null) carts = Collections.emptyList();

        m.addAttribute("carts", carts);
        m.addAttribute("isEmpty", carts.isEmpty());

        // If your design stores grand total on the last Cart row:
        double totalOrderPrice = carts.isEmpty()
                ? 0.0
                : carts.get(carts.size() - 1).getTotalOrderPrice();
        m.addAttribute("totalOrderPrice", totalOrderPrice);

        // If instead each Cart line has its own line total, switch to:
        // double totalOrderPrice = carts.stream().mapToDouble(Cart::getLineTotal).sum();

        return "/user/cart";
    }
    public User getLoggedInUserDetails(Principal p) {

        String email = p.getName();
        User user = userService.getUserByEmail(email);
        return user;
    }


    @GetMapping("cartQuantityUpdate")
    public String cartQuantityUpdate(@RequestParam String sy, @RequestParam Integer cid){

        cartService.cartQuantityUpdate(sy,cid);

        return "redirect:/user/cart";
    }
}
