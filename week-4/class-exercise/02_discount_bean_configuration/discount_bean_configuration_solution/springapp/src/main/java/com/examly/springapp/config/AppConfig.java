package com.examly.springapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.examly.springapp.model.DiscountPolicy;

@Configuration
public class AppConfig {

    @Bean
    public DiscountPolicy studentPolicy() {
        return new DiscountPolicy("STUDENT", 10.0);
    }

    @Bean
    public DiscountPolicy seniorPolicy() {
        return new DiscountPolicy("SENIOR", 20.0);
    }
}
