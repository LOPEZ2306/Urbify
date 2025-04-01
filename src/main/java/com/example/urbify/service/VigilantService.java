package com.example.urbify.service;

import com.example.urbify.models.Vigilant;
import com.example.urbify.repository.VigilantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VigilantService {

    @Autowired
    private VigilantRepository vigilantRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Vigilant save(Vigilant vigilant) {
        vigilant.setPassword(passwordEncoder.encode(vigilant.getPassword()));
        return vigilantRepository.save(vigilant);
    }

    public List<Vigilant> listAllVigilants() {
        return vigilantRepository.findAll();
    }

    public Vigilant getById(Long id) {
        return vigilantRepository.findById(id).orElse(null);
    }

    public Optional<Vigilant> findByEmail(String email) {
        return vigilantRepository.findByEmail(email);
    }

    public void delete(Long id) {
        vigilantRepository.deleteById(id);
    }
}