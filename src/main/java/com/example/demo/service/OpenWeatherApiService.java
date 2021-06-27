package com.example.demo.service;


import com.example.demo.Exception.SearchException;
import com.example.demo.model.Weather;
import com.example.demo.util.TempConverter;
import com.example.demo.util.URLUtil;
import com.google.gson.JsonObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class OpenWeatherApiService {

    String partURL;
    String apikey;



    /**
     *
     * @param city
     * @return
     * @throws IOException
     */
    public Optional<Weather> getWeatherByCity(String city) throws IOException {
        String finalURL = partURL + city + "&unit=standard&appid=" + apikey;
        JsonObject jsonObject;
        try {
            jsonObject = URLUtil.convertURLIntoJsonObject(finalURL);
        } catch (SearchException s) {
            return Optional.empty();
        }
        Weather weather = jsonObjectIntoEntity(city, jsonObject);

        return Optional.of(weather);

    }

    private Weather jsonObjectIntoEntity(String city, JsonObject jsonObject) {
        String description = jsonObject.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").getAsString();
        double tempKelvin = jsonObject.get("main").getAsJsonObject().get("temp").getAsDouble();
        Double temperature = TempConverter.convertKelvinToCels(tempKelvin);
        Double humidity = jsonObject.get("main").getAsJsonObject().get("humidity").getAsDouble();
        String country = jsonObject.get("sys").getAsJsonObject().get("country").toString();

        Weather weather = new Weather(city, description, temperature, humidity, country, LocalDate.now());
        return weather;
    }

    public String getPartURL() {
        return partURL;
    }

    public void setPartURL(String partURL) {
        this.partURL = partURL;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }
}
