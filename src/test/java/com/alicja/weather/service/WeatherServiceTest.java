package com.alicja.weather.service;

import com.alicja.weather.dto.WeatherDto;
import com.alicja.weather.mapper.WeatherMapper;
import com.alicja.weather.model.Weather;
import com.alicja.weather.repository.WeatherRepository;
import com.alicja.weather.util.TempConverter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ContextConfiguration
public class WeatherServiceTest {

    @Mock
    private WeatherRepository weatherRepository;

    @Mock
    private OpenWeatherApiService openWeatherApiService;

    @Autowired
    @Spy
    private WeatherMapper weatherMapper;

    @InjectMocks
    WeatherService weatherService;

    @Test
    public void serviceShouldReturnDtoWeatherByCityFirstTimeFromExternalAPI() throws IOException {
        Double temperature = TempConverter.convertKelvinToCels(304.70);
        Weather weather = new Weather("Madrid", "few clouds", temperature, 25.0, "ES", LocalDate.now());
        WeatherDto weatherDto = weatherMapper.convertFromEntity(weather);

        when(weatherRepository.findWeatherByCity(any())).thenReturn(new ArrayList<>());
        when(openWeatherApiService.getWeatherByCity(any())).thenReturn(Optional.of(weather));
        when(weatherRepository.save(any())).thenReturn(weather);
        Optional<WeatherDto> result = weatherService.getWeatherByCity("Madrid");
        assertEquals(result, Optional.of(weatherDto));

    }

    @Test
    public void serviceShouldReturnDtoWeatherByCityTakeItFromDB() throws IOException {
        Double temperature = TempConverter.convertKelvinToCels(304.70);
        Weather weather = new Weather("Madrid", "few clouds", temperature, 25.0, "ES", LocalDate.now());
        WeatherDto weatherDto = weatherMapper.convertFromEntity(weather);
        when(weatherRepository.findWeatherByCity(any())).thenReturn(List.of(weather));
        Optional<WeatherDto> result = weatherService.getWeatherByCity("Madrid");
        assertEquals(result, Optional.of(weatherDto));

    }


    @Test
    public void testThatWhenTheCityDoesNotExistThenReturnsAnEmptyOptionalWeather() throws IOException {
        when(weatherRepository.findWeatherByCity(any())).thenReturn(new ArrayList<>());
        when(openWeatherApiService.getWeatherByCity(any())).thenReturn(Optional.empty());
        Optional<WeatherDto> result = weatherService.getWeatherByCity("Madrid");

        assertEquals(result,Optional.empty());
    }
}
