package com.examly.springapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.examly.springapp.model.HotelInfo;

@Configuration
public class HotelConfig {

    @Bean
    public HotelInfo hotelInfo(@Value("${hotel.name}") String name,
                               @Value("${hotel.max-nights}") int maxNights,
                               @Value("${hotel.tax-percent}") double taxPercent) {
        return new HotelInfo(name, maxNights, taxPercent);
    }
}
