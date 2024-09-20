package com.gli.asteroids.service;

import com.gli.asteroids.model.AsteroidModel;
import com.gli.asteroids.repository.AsteroidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AsteroidService {

    private static final String API_KEY = "ZoLehhpqZedlTAwMJmrLlegPfoPc7rF2OFhlLKqS";
    private static final String API_URL = "https://api.nasa.gov/neo/rest/v1/feed?start_date=%s&end_date=%s&api_key=" + API_KEY;

    @Autowired
    private AsteroidRepository asteroidRepository;

    public List<AsteroidModel> getAsteroids(String startDate, String endDate) {
        String url = String.format(API_URL, startDate, endDate);
        RestTemplate restTemplate = new RestTemplate();
        Map response = restTemplate.getForObject(url, Map.class);

        List<AsteroidModel> asteroids = new ArrayList<>();
        if (response != null && response.containsKey("near_earth_objects")) {
            Map<String, Object> nearEarthObjects = (Map<String, Object>) response.get("near_earth_objects");

            for (Object dateObject : nearEarthObjects.values()) {
                List<Map<String, Object>> asteroidsList = (List<Map<String, Object>>) dateObject;

                for (Map<String, Object> asteroidObject : asteroidsList) {
                    String name = (String) asteroidObject.get("name");
                    boolean isPotentiallyHazardous = (boolean) asteroidObject.get("is_potentially_hazardous_asteroid");
                    double magnitude = (double) asteroidObject.get("absolute_magnitude_h");

                    AsteroidModel asteroid = new AsteroidModel(name, isPotentiallyHazardous, magnitude);
                    asteroids.add(asteroid);

                    // Simpan ke database
                    asteroidRepository.save(asteroid);
                }
            }
        }
        return asteroids;
    }
    // Create or Update Asteroid
    public AsteroidModel saveAsteroid(AsteroidModel asteroid) {
        return asteroidRepository.save(asteroid);
    }

    // Read All Asteroids
    public List<AsteroidModel> getAllAsteroids() {
        return asteroidRepository.findAll();
    }

    // Read Asteroid by ID
    public Optional<AsteroidModel> getAsteroidById(Long id) {
        return asteroidRepository.findById(id);
    }

    // Update Asteroid
    public AsteroidModel updateAsteroid(Long id, AsteroidModel updatedAsteroid) {
        return asteroidRepository.findById(id).map(asteroid -> {
            asteroid.setName(updatedAsteroid.getName());
            asteroid.setPotentiallyHazardous(updatedAsteroid.isPotentiallyHazardous());
            asteroid.setMagnitude(updatedAsteroid.getMagnitude());
            return asteroidRepository.save(asteroid);
        }).orElseThrow(() -> new RuntimeException("Asteroid not found"));
    }

    // Delete Asteroid by ID
    public void deleteAsteroid(Long id) {
        asteroidRepository.deleteById(id);
    }
}
