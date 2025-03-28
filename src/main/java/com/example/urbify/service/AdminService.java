package com.example.urbify.service;

import com.example.urbify.models.Admin;
import com.example.urbify.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Admin save(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword())); // Encripta la contraseña
        return adminRepository.save(admin);
    }

    public List<Admin> listAll() {
        return adminRepository.findAll();
    }

    public Admin getbyid(Long id) {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        return optionalAdmin.orElse(null); // Devuelve null si no se encuentra el admin
    }

    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    public void delete(Long id) {
        adminRepository.deleteById(id);
    }
}