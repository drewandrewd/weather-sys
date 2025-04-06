package com.example.weather.controller;

import com.example.weather.model.Weather;
import com.example.weather.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherRepository weatherRepository;

    @GetMapping
    public Iterable<Weather> findAll() {
        return weatherRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Weather> findById(@PathVariable int id) {
        return weatherRepository.findById(id);
    }

    @PostMapping
    public ResponseEntity<Weather> save(@RequestBody Weather weather) {
        return weatherRepository.findById(weather.getId()).isPresent()
                ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(weatherRepository.save(weather), HttpStatus.CREATED);
    }
}
