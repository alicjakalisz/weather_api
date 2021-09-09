package com.alicja.weather.util;

public class TempConverter {
    public static double convertKelvinToCels(double kelvinTemp) {
        return kelvinTemp - 273.15;
    }
}
