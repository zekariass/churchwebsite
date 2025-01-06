package com.churchwebsite.churchwebsite.config;

import com.churchwebsite.churchwebsite.services.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AuthSecurityConfig {

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final CustomUserDetailService customUserDetailService;


    @Autowired
    public AuthSecurityConfig(CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler, CustomAuthenticationFailureHandler customAuthenticationFailureHandler, CustomUserDetailService customUserDetailService) {
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
        this.customUserDetailService = customUserDetailService;
    }

    private final String[] publicUrls = {
            "/",
            "/global-search/**",
            "/users/register",
            "/users/login",
            "/users/password/link/request/form",
            "/users/password/link/request/**",
            "/users/password/link/reset/form",
            "/users/password/link/reset/form/**",
            "/users/password/link/request/sent",
            "/users/form/process",
            "/users/register/**",
            "/webjars/**",
            "/resources/**",
            "/assets/**",
            "/css/**",
            "/summernote/**",
            "/js/**",
            "/*.css",
            "/*.js",
            "/*.js.map",
            "/fonts**",
            "/favicon.ico",
            "/error",
            "/media/**",
            "/news",
            "/news/**",
            "/blogs",
            "/blogs/**",
            "/events",
            "/events/**",
            "/media-center",
            "/media-center/**",
            "/church-services",
            "/church-services/**",
            "/members",
            "/members/**",
            "/contactUs",
            "/contactUs/**",
            "/donate",
            "/donate/**",
            "/donation",
            "/donation/**",
            "/donation-success",
            "/donation-cancel",
            "/shopping/products/**",
            "/shopping/cart/**",
            "/aboutUs",
            "/announcements",
            "/announcements/detail",
            "/subscription/email/subscribe"

    };
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authenticationProvider(authenticationProvider());


        httpSecurity.authorizeHttpRequests(
                auth -> {
                    auth.requestMatchers("/shopping/cart/checkout").authenticated();
                    auth.requestMatchers(publicUrls).permitAll();
                    auth.anyRequest().authenticated();
                }
                );

        httpSecurity.formLogin(form -> form.loginPage("/users/login")
                        .successHandler(customAuthenticationSuccessHandler)
                        .failureHandler(customAuthenticationFailureHandler)
//                        .defaultSuccessUrl("/")
                        .permitAll()
                );
        httpSecurity.logout(logout -> {
                    logout.logoutUrl("/users/logout")
                            .logoutSuccessUrl("/?logout");
                })
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults());

        return httpSecurity.build();
    }

//    @Bean
//    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
//        return new CustomAuthenticationFailureHandler();
//    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(customUserDetailService);
        return authenticationProvider;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
