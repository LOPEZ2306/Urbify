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
import java.util.List;

@Controller
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private VigilantService vigilantService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Panel de acciones del administrador
    @GetMapping("/action")
    public String adminAction(Model model, Principal principal) {
        String email = principal.getName();
        Admin admin = adminService.findByEmail(email);
        model.addAttribute("admin", admin);
        return "admin-view/admin-action";
    }

    // Mostrar lista de vigilantes desde el administrador
    @GetMapping("/vigilants")
    public String showVigilantsList(Model model, Principal principal) {
        String email = principal.getName();
        Admin admin = adminService.findByEmail(email);
        List<Vigilant> vigilantes = vigilantService.listAllVigilants();

        model.addAttribute("admin", admin);
        model.addAttribute("vigilantes", vigilantes);
        return "vigilant-view/vigilant-list";
    }

    // Formulario para crear un vigilante (desde el admin)
    @GetMapping("/vigilants/new")
    public String showNewVigilantForm(Model model, Principal principal) {
        Admin admin = adminService.findByEmail(principal.getName());
        model.addAttribute("admin", admin);
        model.addAttribute("vigilant", new Vigilant());
        return "vigilant-view/vigilant-form";
    }

    // Guardar un nuevo vigilante
    @PostMapping("/vigilants")
    public String saveVigilant(@ModelAttribute Vigilant vigilant, Principal principal) {
        Admin admin = adminService.findByEmail(principal.getName());
        if (admin == null) {
            return "redirect:/admins/vigilants?error=admin_not_found";
        }

        vigilant.setAdmin(admin);
        vigilant.setPassword(passwordEncoder.encode(vigilant.getPassword()));
        vigilantService.save(vigilant);

        return "redirect:/admins/vigilants";
    }

    // Formulario para editar un vigilante (desde el admin)
    @GetMapping("/vigilants/edit/{id}")
    public String showFormEditVigilant(@PathVariable Long id, Model model, Principal principal) {
        Vigilant vigilant = vigilantService.getById(id);
        if (vigilant == null) {
            return "redirect:/admins/vigilants";
        }

        Admin admin = adminService.findByEmail(principal.getName());

        model.addAttribute("vigilant", vigilant);
        model.addAttribute("admin", admin);

        return "vigilant-view/vigilant-form";
    }

    @PostMapping("/vigilants/delete/{id}")
    public String deleteVigilant(@PathVariable Long id) {
        Vigilant vigilant = vigilantService.getById(id);

        if (vigilant == null) {
            return "redirect:/admins/vigilants?error=vigilant_not_found";
        }

        try {
            vigilantService.delete(id);
        } catch (Exception e) {
            return "redirect:/admins/vigilants?error=delete_failed";
        }

        return "redirect:/admins/vigilants?success=deleted";
    }
}
