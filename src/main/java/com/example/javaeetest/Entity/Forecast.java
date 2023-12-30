package com.example.javaeetest.Entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Forecast {
    private String city;
    private String data;
    private Double tempC;
    private String weather;

}
