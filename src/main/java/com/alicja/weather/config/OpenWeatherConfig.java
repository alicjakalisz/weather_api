package com.alicja.weather.config;
import com.alicja.weather.service.OpenWeatherApiService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenWeatherConfig {
	//endpoint are properties in properties file like endpoint.apiKey = ...
	//apiKey is a name of atribute in OperaWeatherService Class.
	

    @Bean
    @ConfigurationProperties(prefix="endpoint")
    public OpenWeatherApiService getOpenWeatherApi() {
        return new OpenWeatherApiService();
    }
}
