package com.alicja.weather.controller;

import com.alicja.weather.service.WeatherService;
import com.alicja.weather.dto.WeatherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     *
     * @param city
     * @return
     */
    @GetMapping("/{city}")
    public ResponseEntity<WeatherDto> getWeatherByCity(@PathVariable("city") String city) {
        Optional<WeatherDto> result = weatherService.getWeatherByCity(city);
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(result.get());// from optional returnedby Service
        }
    }
}
