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

    public List<Vigilant> listAll() {
        return vigilantRepository.findAll();
    }

    public Vigilant save(Vigilant vigilant) {
        if (vigilant.getPassword() != null && !vigilant.getPassword().isEmpty()) {
            vigilant.setPassword(passwordEncoder.encode(vigilant.getPassword()));
        }
        return vigilantRepository.save(vigilant);
    }

    public Vigilant getbyid(Long id) {
        Optional<Vigilant> optionalVigilant = vigilantRepository.findById(id);
        return optionalVigilant.orElse(null);
    }

    public void delete(Long id) {
        vigilantRepository.deleteById(id);
    }
}
