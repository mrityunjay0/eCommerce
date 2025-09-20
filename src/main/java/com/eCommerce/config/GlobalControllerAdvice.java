//package com.eCommerce.config;
//
//import com.eCommerce.entity.User;
//import com.eCommerce.service.UserService;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.ui.Model;
//
//import java.security.Principal;
//
//@ControllerAdvice
//public class GlobalControllerAdvice {
//
//    private final UserService userService;
//
//    public GlobalControllerAdvice(UserService userService) {
//        this.userService = userService;
//    }
//
//    @ModelAttribute
//    public void addUserToModel(Principal p, Model model) {
//        if (p != null) {
//            String email = p.getName();
//            User user = userService.getUserByEmail(email);
//            model.addAttribute("user", user);
//            System.out.println("Authenticated user: " + user.getName());
//        }
//    }
//}