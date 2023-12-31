package com.example.javaeetest;

import com.example.javaeetest.Entity.Forecast;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class WeatherAPI {
    private HttpGet get;
    private final Gson gson = new Gson();

    public ArrayList<Forecast> getWeeklyForecast(String path, String cityName){
        get = new HttpGet(path);


        try (CloseableHttpClient closeableHttpClient = HttpClients.createDefault()) {
            // Получаем Json данные из WeatherAPI и записываем его в строку weatherData
            String weatherData = closeableHttpClient.execute(get, classicHttpResponse ->
                    IOUtils.toString(classicHttpResponse.getEntity().getContent(), StandardCharsets.UTF_8));

            // проходимся по полученным Json данным, и записываем их в лист объектов Forecast
            JsonObject jObject = gson.fromJson(weatherData, JsonObject.class);

            JsonObject jsonForecast = jObject.get("forecast").getAsJsonObject();
            JsonArray forecastArray = jsonForecast.getAsJsonArray("forecastday");
            ArrayList<Forecast> forecasts = new ArrayList<>();
            for(int i = 1; i < forecastArray.size(); i++) {

                JsonObject arrayElement = forecastArray.get(i).getAsJsonObject();
                JsonObject day = arrayElement.getAsJsonObject("day");
                JsonObject condition = day.getAsJsonObject("condition");

                forecasts.add(new Forecast(cityName,arrayElement.get("date").getAsString(),
                        day.get("avgtemp_c").getAsDouble(), condition.get("text").getAsString()));
            }
            return forecasts;
        } catch (IOException e) {
            throw new RuntimeException("IOException in getWeeklyForecast method: " + e.getMessage());
        }
    }

    public Forecast getTodayWeather(String path, String cityName){
        get = new HttpGet(path);


        try (CloseableHttpClient closeableHttpClient = HttpClients.createDefault()) {
            // Получаем Json данные из WeatherAPI и записываем его в строку weatherData
            String weatherData = closeableHttpClient.execute(get, classicHttpResponse ->
                    IOUtils.toString(classicHttpResponse.getEntity().getContent(), StandardCharsets.UTF_8));

            // создаем джейсон объект в который записываем полученные ранее данные
            // из него достаем объект хранящий данные о текущей погоде
            // достаем данные о градусах цельсия и записываем в Double
            JsonObject jObject = gson.fromJson(weatherData, JsonObject.class);
            JsonObject location = jObject.get("location").getAsJsonObject();
            String localTime = location.get("localtime").getAsString();
            JsonObject current = jObject.get("current").getAsJsonObject();
            Double tempC = current.get("temp_c").getAsDouble();

            //Достаем объект хранящий информацию о погоде (снег, солнечно, облачно) и достаем эту информацию в String
            JsonObject condition = current.get("condition").getAsJsonObject();
            String weather = condition.get("text").getAsString();

            return new Forecast(cityName, localTime,tempC,weather);
        } catch (IOException e) {
            throw new RuntimeException("IOException in getTodayWeather method: " + e.getMessage());
        }
    }
}
