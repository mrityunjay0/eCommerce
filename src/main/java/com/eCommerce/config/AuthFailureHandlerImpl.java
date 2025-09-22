package com.eCommerce.config;

import com.eCommerce.entity.User;
import com.eCommerce.repository.UserRepository;
import com.eCommerce.service.UserService;
import com.eCommerce.utils.AppConstant;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {

    private final UserRepository userRepository;
    private final UserService userService;

    public AuthFailureHandlerImpl(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {

        String email = request.getParameter("username"); // ensure your login form uses name="username"
        User user = userRepository.findByEmail(email);

        // If user not found, avoid user-enumeration. Show generic message.
        if (user == null) {
            exception = new LockedException("Invalid credentials.");
            setDefaultFailureUrl("/signin?error");
            super.onAuthenticationFailure(request, response, exception);
            return;
        }

        // Enabled check (primitive boolean is fine)
        if (user.isEnabled()) {

            // Null-safe check for non-locked
            if (Boolean.TRUE.equals(user.getAccountNonLocked())) {

                // Null-safe attempts
                int currentAttempts = user.getFailedAttempt() == null ? 0 : user.getFailedAttempt();

                int newAttempts = currentAttempts + 1;
                long max = AppConstant.LOGIN_ATTEMPT;

                if (newAttempts >= max) {
                    // lock now
                    userService.userAccountLock(user);
                    exception = new LockedException("Your account has been locked due to multiple failed attempts. Please try again later.");
                } else {
                    // persist increment and show remaining
                    user.setFailedAttempt(newAttempts);
                    userRepository.save(user);

                    int remaining = Math.toIntExact(max - newAttempts); // e.g., max=3 → after 1st fail remaining=2
                    exception = new LockedException("Invalid credentials. You have " + remaining + " attempt" + (remaining == 1 ? "" : "s") + " remaining.");
                }

            } else {
                // Already locked → try auto-unlock if time elapsed
                boolean unlocked = userService.unlockWhenTimeExpired(user);
                if (!unlocked) {
                    exception = new LockedException("Your account is locked. Try again later.");
                } else {
                    // Auto-unlocked just now; tell them to try again (no enumeration)
                    exception = new LockedException("Your account was locked and is now unlocked. Please try again.");
                }
            }

        } else {
            exception = new LockedException("Account is inactive.");
        }

        setDefaultFailureUrl("/signin?error");
        super.onAuthenticationFailure(request, response, exception);
    }
}