package com.example.urbify.service;

import com.example.urbify.models.Vehicle;
import com.example.urbify.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> listAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Optional<Vehicle> getById(Long id) {
        return vehicleRepository.findById(id);
    }

    public void delete(Long id) {
        vehicleRepository.deleteById(id);
    }

    public boolean existsByPlate(String plate) {
        return vehicleRepository.existsByPlate(plate);
    }
    public boolean existsByIdentification(String identification) {
        return vehicleRepository.existsByIdentification(identification);
    }

}