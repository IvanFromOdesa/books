package com.krylosov_books.books.util.config.security;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
public class WebSecurityConfig {

    /*@Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        return http.build();
    }*/
}

