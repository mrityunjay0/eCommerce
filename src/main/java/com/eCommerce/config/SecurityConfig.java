package com.eCommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final AuthFailureHandlerImpl authFailureHandler;
    private final AuthSuccessHandlerImpl authSuccessHandler;

    public SecurityConfig(UserDetailsService userDetailsService,
                          AuthFailureHandlerImpl authFailureHandler,
                          AuthSuccessHandlerImpl authSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.authFailureHandler = authFailureHandler;
        this.authSuccessHandler = authSuccessHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    // SecurityConfig.java
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        // static
                        .requestMatchers("/css/**","/js/**","/img/**","/webjars/**").permitAll()

                        // public pages
                        .requestMatchers("/","/signin","/register").permitAll()
                        .requestMatchers("/products","/products/**").permitAll()
                        .requestMatchers("/viewDetails/**").permitAll()

                        // search must be public (GET)
                        .requestMatchers(HttpMethod.GET,"/search").permitAll()

                        // auth / account flows that should be public
                        .requestMatchers("/saveUser").permitAll()
                        .requestMatchers("/forgotPassword","/resetPassword","/resetPasswordExecute","/resetPassword/**").permitAll()

                        // protected areas
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasRole("USER")

                        // everything else requires auth
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/signin")
                        .loginProcessingUrl("/login")
                        .failureHandler(authFailureHandler)
                        .successHandler(authSuccessHandler)
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());

        return http.build();
    }
}
