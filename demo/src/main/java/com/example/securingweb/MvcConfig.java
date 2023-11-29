package com.example.securingweb;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        System.out.println("Going to /home");

//        route goes here when the login (user) is successful
//        no unauthorized here at /hello
        registry.addViewController("/hello").setViewName("hello");
        System.out.println("Going to /hello");
        registry.addViewController("/login").setViewName("login");
        System.out.println("Going to /login");
    }

    /**
     * @note  Spring Boot automatically secures all HTTP endpoints with “basic” authentication.
     */

}