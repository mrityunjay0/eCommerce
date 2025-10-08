package com.eCommerce.controller;

import com.eCommerce.entity.*;
import com.eCommerce.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    private OrderService orderService;

    public UserController(CategoryService categoryService, ProductService productService,
                          UserService userService, CartService cartService, OrderService orderService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.userService = userService;
        this.cartService = cartService;
        this.orderService = orderService;
    }

    // To get user details and cart count in the header
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


    // Add to cart
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


    // View cart page
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


    // Safe helper to get logged-in user details
    public User getLoggedInUserDetails(Principal p) {

        String email = p.getName();
        User user = userService.getUserByEmail(email);
        return user;
    }


    // Update cart quantity
    @GetMapping("cartQuantityUpdate")
    public String cartQuantityUpdate(@RequestParam String sy, @RequestParam Integer cid){

        cartService.cartQuantityUpdate(sy,cid);

        return "redirect:/user/cart";
    }


    // Checkout page
    @GetMapping("/checkout")
    public String checkoutPage(Principal p, Model m) {

        User user = getLoggedInUserDetails(p);   // safe helper below

        // If not logged in, render empty amounts gracefully
        if (user == null) {
            m.addAttribute("orderPrice", 0L);
            m.addAttribute("deliveryFee", 250);
            m.addAttribute("tax", 100);
            m.addAttribute("totalOrderPrice", 250 + 100);
            m.addAttribute("isEmpty", true);
            m.addAttribute("carts", Collections.emptyList());
            return "user/checkout"; // no leading slash
        }

        List<Cart> carts = cartService.getCartByUser(user.getId());
        if (carts == null) carts = Collections.emptyList();

        double orderPrice = carts.stream()
                .mapToDouble(c -> c.getTotalPrice())  // ensure this is a number in your entity
                .sum();

        int deliveryFee = 250;
        int tax = 100;
        double totalOrderPrice = orderPrice + deliveryFee + tax;

        m.addAttribute("carts", carts);
        m.addAttribute("isEmpty", carts.isEmpty());
        m.addAttribute("orderPrice", orderPrice);
        m.addAttribute("deliveryFee", deliveryFee);
        m.addAttribute("tax", tax);
        m.addAttribute("totalOrderPrice", totalOrderPrice);

        return "user/checkout";
    }


    // Place order
    @PostMapping("/placeOrder")
    public String placeOrder(@ModelAttribute OrderRequest orderRequest, Principal p, HttpSession session) {

        User user = getLoggedInUserDetails(p);
        orderService.saveOrder(user.getId(), orderRequest);

        session.setAttribute("successMsg", "Order placed successfully!");
        return "user/success";
    }


    // View user's past orders
    @GetMapping("/myOrders")
    public String myOrders(Principal p, Model m) {

        User user = getLoggedInUserDetails(p);
        if (user == null) {
            m.addAttribute("orders", Collections.emptyList());
            m.addAttribute("isEmpty", true);
            return "user/myOrders";
        }

        List<ProductOrder> orders = orderService.getOrdersByUser(user.getId());
        if (orders == null) orders = Collections.emptyList();

        m.addAttribute("orders", orders);
        m.addAttribute("isEmpty", orders.isEmpty());

        return "user/myOrders";
    }

    
    // Cancel order
    @GetMapping("/cancelOrder" )
    public String cancelOrder(@RequestParam Integer id, @RequestParam Integer st, HttpSession session) {

        String msg = orderService.cancelOrder(id, st);
        session.setAttribute("successMsg", msg);
        return "redirect:/user/myOrders";
    }
}
