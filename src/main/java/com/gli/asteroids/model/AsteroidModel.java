package com.gli.asteroids.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class AsteroidModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private boolean isPotentiallyHazardous;
    private double magnitude;

    public AsteroidModel() {}

    public AsteroidModel(String name, boolean isPotentiallyHazardous, double magnitude) {
        this.name = name;
        this.isPotentiallyHazardous = isPotentiallyHazardous;
        this.magnitude = magnitude;
    }
}
