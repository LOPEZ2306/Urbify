package com.example.urbify.repository;

import com.example.urbify.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    boolean existsByPlate(String plate);
    boolean existsByIdentification(String identification);

}