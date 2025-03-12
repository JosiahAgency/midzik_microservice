package com.midziklabs.analytics.model;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class WeatherApiModel {
    public ArrayList<Weather> weather;
    public Main main;
    public int visibility;
    public Wind wind;
    public int timezone;
    public int id;
    public String name;
    public int cod;
}
@Data
@NoArgsConstructor
final class Coord{
    public double lon;
    public double lat;
}
@Data
@NoArgsConstructor
final class Main {
    public double temp;
    public double feels_like;
    public double temp_min;
    public double temp_max;
    public int humidity;
}
@Data
@NoArgsConstructor
final class Weather {
    public int id;
    public String main;
    public String description;
    public String icon;
}

final class Wind {
    public double speed;
    public int deg;
}

