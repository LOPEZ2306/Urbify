package com.example.urbify.service;

import com.example.urbify.models.Admin;
import com.example.urbify.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> listAll() {
        return adminRepository.findAll();
    }

    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }

    public Admin getbyid(Long id) {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        return optionalAdmin.orElse(null); // Devuelve null si no se encuentra el admin
    }

    public void delete(Long id) {
        adminRepository.deleteById(id);
    }
}