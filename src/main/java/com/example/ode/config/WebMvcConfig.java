package com.example.ode.config;

import com.example.ode.handler.LoginHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginHandler loginHandler;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginHandler);
    }
}
