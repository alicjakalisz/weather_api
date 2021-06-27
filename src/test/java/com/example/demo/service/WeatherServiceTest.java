package com.example.demo.service;

import com.example.demo.mapper.WeatherMapper;
import com.example.demo.model.Weather;
import com.example.demo.respository.WeatherRepository;
import com.example.demo.util.TempConverter;
import com.example.demo.weatherdto.WeatherDto;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import javax.print.DocFlavor;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
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

        when(weatherRepository.findWeatherByCity(any())).thenReturn(Optional.empty());
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
        when(weatherRepository.findWeatherByCity(any())).thenReturn(Optional.of(weather));
        Optional<WeatherDto> result = weatherService.getWeatherByCity("Madrid");
        assertEquals(result, Optional.of(weatherDto));

    }


    @Test
    public void testThatWhenTheCityDoesNotExistThenReturnsAnEmptyOptionalWeather() throws IOException {
        when(weatherRepository.findWeatherByCity(any())).thenReturn(Optional.empty());
        when(openWeatherApiService.getWeatherByCity(any())).thenReturn(Optional.empty());
        Optional<WeatherDto> result = weatherService.getWeatherByCity("Madrid");

        assertEquals(result,Optional.empty());
    }
}
