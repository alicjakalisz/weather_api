package com.alicja.weather.config;


import com.alicja.weather.mapper.WeatherMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeatherMapperConfig {

    @Bean
    public WeatherMapper weatherMapper(){
        return new WeatherMapper();
    }
}
