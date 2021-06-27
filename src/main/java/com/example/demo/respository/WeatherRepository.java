package com.example.demo.respository;

import com.example.demo.model.Weather;
import com.example.demo.service.OpenWeatherApiService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather,Long> {

    @Query("SELECT w FROM Weather w WHERE w.city = ?1")
    public Optional<Weather> findWeatherByCity(String city);


}
