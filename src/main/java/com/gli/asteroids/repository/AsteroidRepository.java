package com.gli.asteroids.repository;

import com.gli.asteroids.model.AsteroidModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsteroidRepository extends JpaRepository<AsteroidModel, Long> {
}
