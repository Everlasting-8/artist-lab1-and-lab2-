package com.lab2.servicecartmanagment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CartConfig {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartRepository cartRepository;

    @Bean
    public CartServiceImpl cartService() {
        return new CartServiceImpl(productService, cartRepository);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

