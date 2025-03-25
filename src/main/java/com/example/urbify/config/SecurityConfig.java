package com.example.urbify.config;

import com.example.urbify.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .userDetailsService(customUserDetailsService)
                .authorizeHttpRequests(auth -> auth
                        // Permitir acceso a recursos estáticos y páginas públicas
                        .requestMatchers("/", "/index", "/public/**", "/img/**").permitAll()
                        // Configuración de acceso para administradores
                        .requestMatchers("/admin-view/**", "/admin-action/**", "/vigilant-list/**", "/vigilant-form/**").hasRole("ADMIN")
                        // Configuración de acceso para vigilantes y administradores
                        .requestMatchers("/vigilant-view/**").hasAnyRole("VIGILANT", "ADMIN")
                        // Cualquier otra solicitud requiere autenticación
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        // Página de login
                        .loginPage("/public/login")
                        // URL para procesar el login
                        .loginProcessingUrl("/login")
                        // Manejador de éxito de autenticación personalizado
                        .successHandler(customAuthenticationSuccessHandler())
                        // URL en caso de fallo de login
                        .failureUrl("/public/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        // URL para procesar el logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        // URL de redirección después del logout
                        .logoutSuccessUrl("/public/login?logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                // Configurar CSRF correctamente en lugar de deshabilitarlo
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

                );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
                if (authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                    response.sendRedirect("/admins/action"); // Redirección para admin
                } else if (authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_VIGILANT"))) {
                    response.sendRedirect("/vigilant-view/action"); // Redirección para vigilante
                } else {
                    response.sendRedirect("/"); // Usuario sin rol específico va a la raíz
                }
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}