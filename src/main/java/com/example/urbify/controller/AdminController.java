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

    // Métodos para administradores
    @GetMapping
    public String listAdmins(Model model) {
        model.addAttribute("administradores", adminService.listAllAdmins());
        return "admin-list";
    }

    @GetMapping("/new")
    public String showNewFormAdmin(Model model) {
        model.addAttribute("admin", new Admin());
        return "admin-form";
    }

    @PostMapping
    public String saveAdmin(@ModelAttribute Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminService.save(admin);
        return "redirect:/admins";
    }

    @GetMapping("/edit/{id}")
    public String showFormEditAdmin(@PathVariable Long id, Model model) {
        Admin admin = adminService.getById(id);
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

    @GetMapping("/action")
    public String adminAction(Model model, Principal principal) {
        String email = principal.getName();
        Admin admin = adminService.findByEmail(email);
        model.addAttribute("admin", admin);
        return "admin-view/admin-action";
    }

    @GetMapping("/vigilants")
    public String showVigilantsList(Model model, Principal principal) {
        String email = principal.getName();
        Admin admin = adminService.findByEmail(email);
        List<Vigilant> vigilantes = vigilantService.listAllVigilants();

        model.addAttribute("admin", admin);
        model.addAttribute("vigilantes", vigilantes);
        return "vigilant-view/vigilant-list";
    }

    @GetMapping("/vigilants/new")
    public String showNewVigilantForm(Model model, Principal principal) {
        Admin admin = adminService.findByEmail(principal.getName());
        model.addAttribute("admin", admin);
        model.addAttribute("vigilant", new Vigilant());
        return "vigilant-view/vigilant-form";
    }
}