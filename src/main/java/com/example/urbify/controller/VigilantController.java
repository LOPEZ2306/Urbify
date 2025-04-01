package com.example.urbify.controller;

import com.example.urbify.models.Vigilant;
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
    private PasswordEncoder passwordEncoder;

    // Listar vigilantes
    @GetMapping
    public String listVigilant(Model model) {
        model.addAttribute("vigilantes", vigilantService.listAllVigilants());
        return "vigilant-view/vigilant-list";
    }

    // Mostrar formulario de creación de vigilante
    @GetMapping("/new")
    public String showNewFormVigilant(Model model) {
        model.addAttribute("vigilant", new Vigilant());
        return "vigilant-view/vigilant-form";
    }

    // Guardar vigilante
    @PostMapping
    public String saveVigilant(@ModelAttribute Vigilant vigilant) {
        vigilant.setPassword(passwordEncoder.encode(vigilant.getPassword()));
        vigilantService.save(vigilant);
        return "redirect:/vigilant";
    }

    // Formulario para editar vigilante
    @GetMapping("/edit/{id}")
    public String showFormEditVigilant(@PathVariable Long id, Model model) {
        Vigilant vigilant = vigilantService.getById(id);
        if (vigilant == null) {
            return "redirect:/vigilant";
        }

        model.addAttribute("vigilant", vigilant);
        model.addAttribute("admin", vigilant.getAdmin());

        return "vigilant-view/vigilant-form";
    }

    // Eliminar vigilante
    @PostMapping("/delete/{id}")
    public String deleteVigilant(@PathVariable Long id) {
        Vigilant vigilant = vigilantService.getById(id);

        if (vigilant == null) {
            return "redirect:/vigilant?error=vigilant_not_found";
        }

        try {
            vigilantService.delete(id);
        } catch (Exception e) {
            return "redirect:/vigilant?error=delete_failed";
        }

        return "redirect:/vigilant?success=deleted";
    }

    // Panel de acciones del vigilante
    @GetMapping("/action")
    public String vigilantAction(Model model, Principal principal) {
        String email = principal.getName();
        Vigilant vigilant = vigilantService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Vigilante no encontrado"));

        model.addAttribute("vigilant", vigilant);
        return "vigilant-view/vigilant-action";
    }
}
