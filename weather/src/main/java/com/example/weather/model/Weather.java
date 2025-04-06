package com.example.weather.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Weather {

    @Id
    @GeneratedValue
    private int id;

    @NonNull
    private double latitude;
    @NonNull
    private double longitude;
    @NonNull
    private String condition;
    @NonNull
    private double temperature;

    public Weather(@NonNull double latitude, @NonNull double longitude, @NonNull String condition, @NonNull double temperature) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.condition = condition;
        this.temperature = temperature;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
