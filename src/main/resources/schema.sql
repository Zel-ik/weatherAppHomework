DROP TABLE IF EXISTS weather_forecast;

CREATE TABLE IF NOT EXISTS weather_forecast(
    forecast_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    city VARCHAR(30) NOT NULL,
    weather VARCHAR(20) NOT NULL,
    avg_temp_c INTEGER NOT NULL,
    forecast_date DATE NOT NULL,
    last_request_time TIME NOT NULL
);