package com.example.javaeetest.Entity;


import lombok.*;

import java.time.LocalTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Forecast {
    private int id;
    private String city;
    private String data;
    private Double tempC;
    private String weather;
    private LocalTime lastRequestTime;

    public Forecast(String city, String data, Double tempC, String weather) {
        this.city = city;
        this.data = data;
        this.tempC = tempC;
        this.weather = weather;
    }
}
