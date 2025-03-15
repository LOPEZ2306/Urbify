package com.example.urbify.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/public/**", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/admins/**").hasRole("ADMIN")
                        .requestMatchers("/vigilant-view/**").hasAnyRole("VIGILANT", "ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/public/login")
                        .loginProcessingUrl("/login")
                        .successHandler(new SavedRequestAwareAuthenticationSuccessHandler() { // Usa el manejador predeterminado
                            @Override
                            public void onAuthenticationSuccess(
                                    jakarta.servlet.http.HttpServletRequest request,
                                    jakarta.servlet.http.HttpServletResponse response,
                                    Authentication authentication
                            ) throws jakarta.servlet.ServletException, java.io.IOException {
                                // Redirige a la URL solicitada originalmente o a la raíz
                                super.onAuthenticationSuccess(request, response, authentication);
                            }
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/public/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin123")
                .roles("ADMIN")
                .build();

        UserDetails vigilant = User.withDefaultPasswordEncoder()
                .username("vigilant")
                .password("123")
                .roles("VIGILANT")
                .build();

        return new InMemoryUserDetailsManager(admin, vigilant);
    }
}