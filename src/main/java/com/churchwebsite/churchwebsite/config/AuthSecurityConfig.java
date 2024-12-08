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
    private final CustomUserDetailService customUserDetailService;


    @Autowired
    public AuthSecurityConfig(CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler, CustomUserDetailService customUserDetailService) {
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        this.customUserDetailService = customUserDetailService;
    }

    private final String[] publicUrls = {
            "/",
            "/global-search/**",
            "/users/register",
            "/users/processUserRegistration",
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
            "/media/**"
    };
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authenticationProvider(authenticationProvider());


        httpSecurity.authorizeHttpRequests(
                auth -> {
                    auth.requestMatchers(publicUrls).permitAll();
                    auth.anyRequest().authenticated();
                }
                );

        httpSecurity.formLogin(form -> form.loginPage("/users/login")
                        .successHandler(customAuthenticationSuccessHandler)
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
