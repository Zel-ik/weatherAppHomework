package com.example.javaeetest;

import com.example.javaeetest.Entity.Forecast;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "TodayWeatherServlet", value ="/today-weather")
public class TodayWeatherServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String cityName = request.getParameter("city");
        String path = String.format("http://api.weatherapi.com/v1/current.json" +
                "?key=4dc9cb99ac514a14ae6161312232812&q=%s&aqi=no", cityName);

        Forecast forecast = new WeatherAPI().getTodayWeather(path, cityName);

        //Отправляем полученные данные о погоде в браузер
        PrintWriter pw = response.getWriter();
        pw.println(String.format("Now %s. In %s we have %f°C (%s)", forecast.getData(),
                forecast.getCity(), forecast.getTempC(), forecast.getWeather()));
        pw.flush();
        pw.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
