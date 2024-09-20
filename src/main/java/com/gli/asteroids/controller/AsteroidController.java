package com.gli.asteroids.controller;

import com.gli.asteroids.model.AsteroidModel;
import com.gli.asteroids.service.AsteroidService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "AsteroidController", description = "Asteroid Application Controller")
public class AsteroidController {

    @Autowired
    private AsteroidService asteroidService;

    @GetMapping("/fetch")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Test")})
    public List<AsteroidModel> index(@RequestParam(name = "startDate", defaultValue = "2015-09-07") String startDate,
                                     @RequestParam(name = "endDate", defaultValue = "2015-09-08") String endDate) {
        return asteroidService.getAsteroids(startDate, endDate);
    }

    // Create or Update Asteroid
    @PostMapping("/create")
    public ResponseEntity<AsteroidModel> createOrUpdateAsteroid(@RequestBody AsteroidModel asteroid) {
        AsteroidModel savedAsteroid = asteroidService.saveAsteroid(asteroid);
        return ResponseEntity.ok(savedAsteroid);
    }

    // Read All Asteroids
    @GetMapping("/asteroids")
    public ResponseEntity<List<AsteroidModel>> getAllAsteroids() {
        List<AsteroidModel> asteroids = asteroidService.getAllAsteroids();
        return ResponseEntity.ok(asteroids);
    }

    // Read Asteroid by ID
    @GetMapping("/asteroids/{id}")
    public ResponseEntity<AsteroidModel> getAsteroidById(@PathVariable Long id) {
        Optional<AsteroidModel> asteroid = asteroidService.getAsteroidById(id);
        return asteroid.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update Asteroid by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<AsteroidModel> updateAsteroid(@PathVariable Long id, @RequestBody AsteroidModel updatedAsteroid) {
        AsteroidModel asteroid = asteroidService.updateAsteroid(id, updatedAsteroid);
        return ResponseEntity.ok(asteroid);
    }

    // Delete Asteroid by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAsteroid(@PathVariable Long id) {
        asteroidService.deleteAsteroid(id);
        return ResponseEntity.ok().build();
    }
}
