package com.example.urbify.controller;

import org.springframework.ui.Model;
import com.example.urbify.models.Vigilant;
import com.example.urbify.service.VigilantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vigilants")
public class VigilantController {

    @Autowired
    private VigilantService vigilantService;

    @GetMapping
    public String listVigilant(Model model) {
        model.addAttribute("vigilantes", vigilantService.listAll()); // Cambia "vigilant" a "vigilantes"
        return "vigilant-list";
    }

    @GetMapping("/new")
    public String showNewFormVigilant(Model model) {
        model.addAttribute("vigilant", new Vigilant());
        return "vigilant-form";
    }

    @PostMapping
    public String saveVigilant(Vigilant vigilant) {
        vigilantService.save(vigilant);
        return "redirect:/vigilants";
    }

    @GetMapping("/edit/{id}")
    public String showFormEditVigilant(@PathVariable Long id, Model model) {
        Vigilant vigilant = vigilantService.getbyid(id);
        model.addAttribute("vigilant", vigilant);
        return "vigilant-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteVigilant(@PathVariable Long id) {
        vigilantService.delete(id);
        return "redirect:/vigilants";
    }
}