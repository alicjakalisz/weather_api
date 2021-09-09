package com.alicja.weather.service;

import com.alicja.weather.mapper.WeatherMapper;
import com.alicja.weather.model.Weather;
import com.alicja.weather.repository.WeatherRepository;;
import com.alicja.weather.dto.WeatherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
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
        List<Weather> weatherByCity = weatherRepository.findWeatherByCity(city);

        if (!weatherByCity.isEmpty()) {
            Weather latestWeatherCity = weatherByCity.get(0);
            if (latestWeatherCity.getDate().equals(LocalDate.now())) {
                WeatherDto weatherDto = weatherMapper.convertFromEntity(latestWeatherCity);
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
