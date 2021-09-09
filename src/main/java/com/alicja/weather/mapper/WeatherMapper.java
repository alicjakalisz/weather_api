package com.alicja.weather.mapper;

import com.alicja.weather.dto.WeatherDto;
import com.alicja.weather.model.Weather;

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
