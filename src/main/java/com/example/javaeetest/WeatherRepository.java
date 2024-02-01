package com.example.javaeetest;

import com.example.javaeetest.Entity.Forecast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class WeatherRepository {
    public static void main(String[] args) {
        ArrayList<Forecast> forecasts = getWeeklyForecast("SELECT * FROM weather_forecast");

        for(Forecast f : forecasts){
            System.out.println(f.toString());
        }
    }

    public static ArrayList<Forecast> getWeeklyForecast(String query){
        ArrayList<Forecast> forecasts = new ArrayList<>();

        try(Connection connection = DBConfig.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet res = preparedStatement.executeQuery();

            while(res.next()){
                int id = res.getInt("forecast_id");
                String city = res.getString("city");
                LocalDate forecastDay = res.getDate("forecast_date").toLocalDate();
                String weather = res.getString("weather");
                double tempC = res.getInt("avg_temp_c");
                LocalTime lastRequestTime =  res.getTime("last_request_time").toLocalTime();

                forecasts.add(new Forecast(id, city, forecastDay.toString(), tempC,weather, lastRequestTime));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return forecasts;
    }
}
