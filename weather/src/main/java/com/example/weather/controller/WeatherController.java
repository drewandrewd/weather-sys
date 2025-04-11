package com.example.weather.controller;

import com.example.weather.model.Main;
import com.example.weather.model.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class WeatherController {

    @Autowired
    @Qualifier("apiRestTemplate")
    private RestTemplate restTemplate;

    @Value("${appid}")
    private String appId;

    @Value("${url.weather}")
    private String urlWeather;

    @Cacheable(value = "weather", key = "#lat + '-' + #lon")
    @GetMapping("/weather")
    public Main getWeather(@RequestParam String lat, @RequestParam String lon) {
        String request = String.format("%s?lat=%s&lon=%s&units=metric&appid=%s",
                urlWeather, lat, lon, appId);
        Root response = restTemplate.getForObject(request, Root.class);
        return response != null ? response.getMain() : null;
    }
}
