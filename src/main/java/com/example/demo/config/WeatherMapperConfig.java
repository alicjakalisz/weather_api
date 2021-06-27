package com.example.demo.config;


import com.example.demo.mapper.WeatherMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeatherMapperConfig {

    @Bean
    public WeatherMapper weatherMapper(){
        return new WeatherMapper();
    }
}
