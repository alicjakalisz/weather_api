package com.example.demo.service;

import com.example.demo.mapper.WeatherMapper;
import com.example.demo.model.Weather;
import com.example.demo.respository.WeatherRepository;;
import com.example.demo.weatherdto.WeatherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class WeatherService {

    private WeatherRepository weatherRepository;
    private OpenWeatherApiService openWeatherApiService;
    private WeatherMapper weatherMapper;

    @Autowired
    public WeatherService(WeatherRepository weatherRepository, OpenWeatherApiService openWeatherApiService, WeatherMapper weatherMapper) {
        this.weatherRepository = weatherRepository;
        this.openWeatherApiService = openWeatherApiService;
        this.weatherMapper = weatherMapper;
    }

    /**
     * @param city
     * @return
     */
    public Optional<WeatherDto> getWeatherByCity(String city) {
        Optional<Weather> weather = weatherRepository.findWeatherByCity(city);
        if (weather.isPresent()) {
            if (weather.get().getDate().equals(LocalDate.now())) {
                WeatherDto weatherDto = weatherMapper.convertFromEntity(weather.get());
                return Optional.of(weatherDto);
            } else {
                return getWeatherFromApi(city);
            }
        } else {
            return getWeatherFromApi(city);
        }
    }

    /**
     * @param city
     * @return
     */
    private Optional<WeatherDto> getWeatherFromApi(String city) {
        Optional<Weather> weatherFromApi;
        try {
            weatherFromApi = openWeatherApiService.getWeatherByCity(city); // weather or null
            if (weatherFromApi.isPresent()) {
                weatherRepository.save(weatherFromApi.get());
                WeatherDto weatherDtoFromApi = weatherMapper.convertFromEntity(weatherFromApi.get());
                return Optional.of(weatherDtoFromApi);
            } else {
                return Optional.empty();
            }

        } catch (IOException e) {
            e.getMessage();
            return Optional.empty();
        }
    }
}
