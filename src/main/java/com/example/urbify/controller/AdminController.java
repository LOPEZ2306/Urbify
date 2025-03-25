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
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private VigilantService vigilantService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Métodos para administradores
    @GetMapping
    public String listAdmin(Model model) {
        model.addAttribute("administrador", adminService.listAll());
        return "admin-list";
    }

    @GetMapping("/new")
    public String showNewFormAdmin(Model model) {
        model.addAttribute("admin", new Admin());
        return "admin-form";
    }

    @PostMapping
    public String saveAdmin(@ModelAttribute Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword())); // Encripta la contraseña
        adminService.save(admin);
        return "redirect:/admins";
    }

    @GetMapping("/edit/{id}")
    public String showFormEditAdmin(@PathVariable Long id, Model model) {
        Admin admin = adminService.getbyid(id);
        if (admin == null) {
            return "redirect:/admins";
        }
        model.addAttribute("admin", admin);
        return "admin-form";
    }

    @PostMapping("/delete/{id}")
    public String deleteAdmin(@PathVariable Long id) {
        adminService.delete(id);
        return "redirect:/admins";
    }

    // Métodos para gestionar vigilantes
    @GetMapping("/vigilants")
    public String listVigilant(Model model) {
        model.addAttribute("vigilantes", vigilantService.listAll());
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
        String adminEmail = principal.getName(); // El email del admin logueado
        Admin admin = adminService.findByEmail(adminEmail);

        if (admin != null) {
            vigilant.setAdmin(admin);
        } else {
            // Manejar el caso donde el admin no existe (opcional)
            return "redirect:/admins/vigilants?error=admin_not_found";
        }

        vigilant.setPassword(passwordEncoder.encode(vigilant.getPassword()));
        vigilantService.save(vigilant);
        return "redirect:/admins/vigilants";
    }

    @GetMapping("/vigilants/edit/{id}")
    public String showFormEditVigilant(@PathVariable Long id, Model model) {
        Vigilant vigilant = vigilantService.getbyid(id);
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

    // Nueva ruta para la acción de administrador después del login
    @GetMapping("/action")
    public String adminAction() {
        return "admin-view/admin-action"; // Retorna la plantilla admin-action.html
    }
}
