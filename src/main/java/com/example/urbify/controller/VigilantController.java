package com.example.urbify.controller;

import com.example.urbify.models.Admin;
import com.example.urbify.models.Vigilant;
import com.example.urbify.service.AdminService;
import com.example.urbify.service.VigilantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@Controller
@RequestMapping("/vigilant")
public class VigilantController {

    @Autowired
    private VigilantService vigilantService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Métodos para gestionar vigilantes
    @GetMapping("/vigilant")
    public String listVigilant(Model model) {
        model.addAttribute("vigilantes", vigilantService.listAllVigilants());
        return "vigilant-view/vigilant-list";
    }

    @GetMapping("/vigilants/new")
    public String showNewFormVigilant(Model model) {
        model.addAttribute("vigilant", new Vigilant());
        return "vigilant-view/vigilant-form";
    }

    @PostMapping("/vigilants")
    public String saveVigilant(@ModelAttribute Vigilant vigilant, Principal principal) {
        // Obtener el admin actualmente autenticado
        String adminEmail = principal.getName();
        Admin admin = adminService.findByEmail(adminEmail);

        if (admin != null) {
            vigilant.setAdmin(admin);
        } else {
            return "redirect:/admins/vigilants?error=admin_not_found";
        }

        vigilant.setPassword(passwordEncoder.encode(vigilant.getPassword()));
        vigilantService.save(vigilant);
        return "redirect:/admins/vigilants";
    }

    @GetMapping("/vigilants/edit/{id}")
    public String showFormEditVigilant(@PathVariable Long id, Model model) {
        Vigilant vigilant = vigilantService.getById(id);
        if (vigilant == null) {
            return "redirect:/admins/vigilants";
        }
        model.addAttribute("vigilant", vigilant);
        return "vigilant-view/vigilant-form";
    }

    @PostMapping("/vigilants/delete/{id}")
    public String deleteVigilant(@PathVariable Long id) {
        vigilantService.delete(id);
        return "redirect:/admins/vigilants";
    }

    @GetMapping("/action")
    public String vigilantAction(Model model, Principal principal) {
        String email = principal.getName();
        Vigilant vigilant = vigilantService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Vigilante no encontrado"));

        model.addAttribute("vigilant", vigilant);
        return "vigilant-view/vigilant-action";
    }
}