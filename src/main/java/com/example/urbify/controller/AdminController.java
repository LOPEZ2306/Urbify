package com.example.urbify.controller;

import com.example.urbify.models.Admin;
import com.example.urbify.models.Vigilant;
import com.example.urbify.service.AdminService;
import com.example.urbify.service.VigilantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private VigilantService vigilantService;

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
    public String saveAdmin(Admin admin) {
        adminService.save(admin);
        return "redirect:/admins";
    }

    @GetMapping("/edit/{id}")
    public String showFormEditAdmin(@PathVariable Long id, Model model) {
        Admin admin = adminService.getbyid(id);
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
    public String saveVigilant(@ModelAttribute Vigilant vigilant) {
        vigilantService.save(vigilant);
        return "redirect:/admins/vigilants";
    }

    @GetMapping("/vigilants/edit/{id}")
    public String showFormEditVigilant(@PathVariable Long id, Model model) {
        Vigilant vigilant = vigilantService.getbyid(id);
        model.addAttribute("vigilant", vigilant);
        return "vigilant-view/vigilant-form";
    }

    @PostMapping("/vigilants/delete/{id}")
    public String deleteVigilant(@PathVariable Long id) {
        vigilantService.delete(id);
        return "redirect:/admins/vigilants";
    }
}