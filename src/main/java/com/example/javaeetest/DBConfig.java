package com.example.javaeetest;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {

    private static final String  DBUrl = "jdbc:postgresql://localhost:5432/WeatherApi";
    private static final String DBName = "postgres";
    private static final String DBPassword = "admin";

    public static Connection getConnection(){
            Connection connection = null;
            try{
                connection = DriverManager.getConnection(DBUrl, DBName, DBPassword);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return connection;
        }
}
