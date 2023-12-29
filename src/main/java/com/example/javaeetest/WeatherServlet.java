package com.example.javaeetest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "WeatherServlet")
public class WeatherServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String cityName = request.getParameter("city");
        // Сделать String Format
        String path = "http://api.weatherapi.com/v1/current.json?key=4dc9cb99ac514a14ae6161312232812&q=" + cityName
                + "&aqi=no";

        HttpGet get = new HttpGet(path);
        Gson gson = new Gson();

        String userResponse;

        try (CloseableHttpClient closeableHttpClient = HttpClients.createDefault()) {
            // Получаем Json и записываем его в строку geoData
            String geoData = closeableHttpClient.execute(get, classicHttpResponse ->
                    IOUtils.toString(classicHttpResponse.getEntity().getContent(), StandardCharsets.UTF_8));

            // создаем джейсон объект в который записываем полученные ранее данные
            // из него достаем объект хранящий данные о текущей погоде
            // достаем данные о градусах цельсия и записываем в Double
            JsonObject jObject = gson.fromJson(geoData, JsonObject.class);
            JsonObject current = jObject.get("current").getAsJsonObject();
            Double tempC = current.get("temp_c").getAsDouble();

            //Достаем объект хранящий информацию о погоде (снег, солнечно, облачно) и достаем эту информацию в String
            JsonObject condition = current.get("condition").getAsJsonObject();
            String weather = condition.get("text").getAsString();

            userResponse = tempC + "°C (" + weather + ")";
        }

        //Отправляем полученные данные о погоде в браузер
        PrintWriter pw = response.getWriter();
        pw.println(userResponse);
        pw.flush();
        pw.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
