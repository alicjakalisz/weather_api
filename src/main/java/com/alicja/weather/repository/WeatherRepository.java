package com.alicja.weather.repository;

import com.alicja.weather.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<Weather,Long> {

    @Query(value="SELECT w FROM Weather w WHERE w.city = ?1 ORDER BY w.date DESC")
    public List<Weather> findWeatherByCity(String city);

}
