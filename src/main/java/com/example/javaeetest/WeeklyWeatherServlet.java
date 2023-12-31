package com.example.javaeetest;

import com.example.javaeetest.Entity.Forecast;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "WeeklyWeatherServlet", value = "/weekly-weather")
public class WeeklyWeatherServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cityName = request.getParameter("city");
        String path = String.format("http://api.weatherapi.com/v1/forecast.json" +
                "?key=4dc9cb99ac514a14ae6161312232812&q=%s&days=7&aqi=no&alerts=yes", cityName);

        ArrayList<Forecast> forecasts = new WeatherAPI().getWeeklyForecast(path, cityName);
        PrintWriter pw = response.getWriter();

        for (Forecast forecast : forecasts) {
            pw.println(String.format("Date is %s. In %s we have %f°C (%s)", forecast.getData(),
                    forecast.getCity(), forecast.getTempC(), forecast.getWeather()));
            pw.println();
        }

        pw.flush();
        pw.close();
    }
}
