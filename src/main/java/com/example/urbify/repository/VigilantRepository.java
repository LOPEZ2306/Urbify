package com.example.urbify.repository;

import com.example.urbify.models.Vigilant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VigilantRepository extends JpaRepository<Vigilant, Long> {
    Optional<Vigilant> findByEmail(String email);
}