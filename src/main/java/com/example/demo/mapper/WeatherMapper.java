package com.example.demo.mapper;

import com.example.demo.model.Weather;
import com.example.demo.weatherdto.WeatherDto;

public class WeatherMapper implements Mapper<WeatherDto, Weather> {
    @Override
    public WeatherDto convertFromEntity(Weather entity) {
        return new WeatherDto(entity.getCity(), entity.getDescription(), entity.getTemperature(), entity.getHumidity(), entity.getCountry(), entity.getDate());
    }

    @Override
    public Weather convertFromDto(WeatherDto dto) {
        return null;
    }
}
