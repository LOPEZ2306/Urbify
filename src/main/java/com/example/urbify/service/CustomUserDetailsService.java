package com.example.urbify.service;

import com.example.urbify.models.Admin;
import com.example.urbify.models.Vigilant;
import com.example.urbify.repository.AdminRepository;
import com.example.urbify.repository.VigilantRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final VigilantRepository vigilantRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Evita el ciclo

    public CustomUserDetailsService(AdminRepository adminRepository, VigilantRepository vigilantRepository) {
        this.adminRepository = adminRepository;
        this.vigilantRepository = vigilantRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Comparación de contraseñas sin inyectar PasswordEncoder
        Admin admin = adminRepository.findByEmail(email).orElse(null);
        if (admin != null) {
            return new User(admin.getEmail(), admin.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        }

        Vigilant vigilant = vigilantRepository.findByEmail(email).orElse(null);
        if (vigilant != null) {
            return new User(vigilant.getEmail(), vigilant.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_VIGILANT")));
        }

        throw new UsernameNotFoundException("Usuario no encontrado");
    }
}
