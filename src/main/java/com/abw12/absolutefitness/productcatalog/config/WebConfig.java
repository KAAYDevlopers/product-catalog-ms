package com.abw12.absolutefitness.productcatalog.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply CORS settings to all endpoints
                .allowedOrigins("http://localhost:3000") // Allow frontend domain
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed HTTP methods
                .allowedHeaders("*") // Allow all headers
                .allowCredentials(true); // Allow credentials
    }
}