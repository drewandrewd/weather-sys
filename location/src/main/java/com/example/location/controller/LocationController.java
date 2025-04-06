package com.example.location.controller;

import com.example.location.model.Location;
import com.example.location.repository.LocationRepository;
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
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    @GetMapping
    public Iterable<Location> findAll() {
        return locationRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Location> findById(@PathVariable int id) {
        return locationRepository.findById(id);
    }

    @PostMapping
    public ResponseEntity<Location> save(@RequestBody Location location) {
        return locationRepository.findById(location.getId()).isPresent()
                ? new ResponseEntity(locationRepository.findById(location.getId()), HttpStatus.BAD_REQUEST)
                : new ResponseEntity(locationRepository.save(location), HttpStatus.CREATED);
    }
}
