package com.example.location.controller;

import com.example.location.model.Location;
import com.example.location.model.Weather;
import com.example.location.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationRepository repository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private org.springframework.cloud.client.discovery.DiscoveryClient discoveryClient;

    @GetMapping("/services")
    public List<String> getServices() {
        return discoveryClient.getServices(); // должен вернуть ["weather", ...]
    }

    @PostMapping
    public Location save(@RequestBody Location location) {
        return repository.save(location);
    }

    @PutMapping
    public ResponseEntity<Location> update(@RequestParam String name, @RequestBody Location location) {
        if (!repository.existsByName(name)) {
            return ResponseEntity.notFound().build();
        }
        Location oldData = repository.findByName(name).get();
        location.setId(oldData.getId());
        repository.save(location);
        return ResponseEntity.ok(location);
    }

    @GetMapping
    public ResponseEntity<?> getLocation(@RequestParam(required = false) String name) {
        if (name != null) {
            return repository.findByName(name)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.ok(repository.findAll());
        }
    }

    @GetMapping("/weather")
    public Weather redirectRequestWeather(@RequestParam String name) {
        Location location = repository.findByName(name).get();
        System.out.println(name);
        String url = String.format("http://weather/weather?lat=%s&lon=%s", location.getLatitude(), location.getLongitude());
        return restTemplate.getForObject(url, Weather.class);
    }

    @DeleteMapping
    public void delete(@RequestParam String name) {
        repository.delete(repository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Location not found: " + name)));
    }
}