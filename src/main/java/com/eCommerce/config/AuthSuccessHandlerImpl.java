package com.eCommerce.config;

import com.eCommerce.entity.User;
import com.eCommerce.repository.UserRepository;
import com.eCommerce.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

@Component
public class AuthSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private final UserRepository userRepository; // or inject UserService if you prefer
    private final UserService userService;

    public AuthSuccessHandlerImpl(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // Reset failed attempts & clear lock info
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setFailedAttempt(0);
//            user.setLockTime(null);
            userRepository.save(user);
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Set<String> roles = AuthorityUtils.authorityListToSet(authorities);

        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/admin/");
        }
        else if (roles.contains("ROLE_USER")) {
            response.sendRedirect("/user/home");
        }
        else {
            response.sendRedirect("/");
        }
    }
}