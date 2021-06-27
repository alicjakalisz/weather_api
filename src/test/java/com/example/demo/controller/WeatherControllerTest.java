package com.example.demo.controller;

import com.example.demo.service.WeatherService;
import com.example.demo.weatherdto.WeatherDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Optional;

@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {
    //to mock tomcat
    @Autowired
    private MockMvc mockMvc;
    ///to mock service
    @MockBean
    private WeatherService weatherService;

    @Test
    public void weatherControllerShouldReturnWeatherBasedOnCityVariable() throws Exception {
        WeatherDto weatherDto = new WeatherDto(
                "Madrid",
                "few clouds",
                304.7,
                25.00,
                "Spain",
                LocalDate.of(2021, 06, 21));
        //mocking service
        when(weatherService.getWeatherByCity(anyString())).thenReturn(java.util.Optional.of(weatherDto));
        //mock response to the client
        ResultActions resultActions = mockMvc.perform(get("/weather/Madrid"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith("application/json"));
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        //from String Json to Entity
        WeatherDto result = objectMapper.readValue(contentAsString, WeatherDto.class);
        Assertions.assertEquals(weatherDto, result);
    }

    @Test
    public void controllerShouldReturnNoContentInCaseCityNotFound() throws Exception {
        when(weatherService.getWeatherByCity(anyString())).thenReturn(Optional.empty());
        mockMvc.perform(get("/weather/XXX")).andDo(print()).andExpect(status().isNoContent());
    }
}
