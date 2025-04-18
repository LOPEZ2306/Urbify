package com.example.urbify.controller;

import com.example.urbify.models.Person;
import com.example.urbify.models.Vehicle;
import com.example.urbify.models.Vigilant;
import com.example.urbify.service.PersonService;
import com.example.urbify.service.VehicleService;
import com.example.urbify.service.VigilantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/vigilant")
public class VigilantController {

    @Autowired
    private VigilantService vigilantService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private PersonService personService;

    // Panel de acciones del vigilante
    @GetMapping("/action")
    public String vigilantAction(Model model, Principal principal) {
        Vigilant vigilant = vigilantService.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Vigilante no encontrado"));

        model.addAttribute("vigilant", vigilant);
        return "vigilant-view/vigilant-action";
    }

    // Listar vehículos
    @GetMapping("/vehicles")
    public String showVehiclesList(Model model, Principal principal) {
        Vigilant vigilant = vigilantService.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Vigilante no encontrado"));

        List<Vehicle> vehicles = vehicleService.listAllVehicles();

        model.addAttribute("vigilant", vigilant);
        model.addAttribute("vehicles", vehicles);

        return "visitors-view/vehicle-list";
    }

    // Mostrar formulario de nuevo vehículo
    @GetMapping("/vehicles/new")
    public String showNewVehicleForm(Model model, Principal principal) {
        Vigilant vigilant = vigilantService.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Vigilante no encontrado"));

        model.addAttribute("vigilant", vigilant);
        model.addAttribute("vehicle", new Vehicle());
        return "visitors-view/vehicle-form";
    }

    // Guardar nuevo vehículo (con manejo de errores)
    @PostMapping("/vehicles")
    public String saveVehicle(@ModelAttribute Vehicle vehicle, Principal principal,
                              Model model) {
        Vigilant vigilant = vigilantService.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Vigilante no encontrado"));

        // Verificar si la placa ya existe
        if (vehicleService.existsByPlate(vehicle.getPlate())) {
            model.addAttribute("error", "La placa del vehículo ya está registrada");
            model.addAttribute("vigilant", vigilant);
            model.addAttribute("vehicle", vehicle);
            return "visitors-view/vehicle-form";
        }

        // Verificar si la identificación ya existe
        if (vehicleService.existsByIdentification(vehicle.getIdentification())) {
            model.addAttribute("error", "La identificación ya está registrada");
            model.addAttribute("vigilant", vigilant);
            model.addAttribute("vehicle", vehicle);
            return "visitors-view/vehicle-form";
        }

        // guarda si esta todo esta bien
        vehicle.setVigilant(vigilant);
        vehicle.setCreatedAt(new Date());
        vehicle.setUpdatedAt(new Date());
        vehicle.setActive(true);
        vehicleService.save(vehicle);

        return "redirect:/vigilant/vehicles?success=Vehículo registrado exitosamente";
    }

    // Mostrar formulario de edición
    @GetMapping("/vehicles/edit/{id}")
    public String showEditVehicleForm(@PathVariable Long id, Model model, Principal principal) {
        Vehicle vehicle = vehicleService.getById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        Vigilant vigilant = vigilantService.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Vigilante no encontrado"));

        model.addAttribute("vigilant", vigilant);
        model.addAttribute("vehicle", vehicle);
        return "visitors-view/vehicle-form";
    }

    // Eliminar vehículo
    @PostMapping("/vehicles/delete/{id}")
    public String deleteVehicle(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            vehicleService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Vehículo eliminado exitosamente");
            return "redirect:/vigilant/vehicles";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el vehículo");
            return "redirect:/vigilant/vehicles";
        }
    }

    //listar personas
    @GetMapping("/persons")
    public String showPersonList(Model model, Principal principal) {
        Vigilant vigilant = vigilantService.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Vigilante no encontrado"));

        List<Person> persons = personService.listAllPerson();

        model.addAttribute("vigilant", vigilant);
        model.addAttribute("persons", persons);

        return "visitors-view/person-list";
    }

    // Mostrar formulario de nuevo Persona
    @GetMapping("/persons/new")
    public String showNewPersonForm(Model model, Principal principal) {
        Vigilant vigilant = vigilantService.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Vigilante no encontrado"));

        model.addAttribute("vigilant", vigilant);
        model.addAttribute("person", new Person());
        return "visitors-view/person-form";
    }

    // Guardar nuevo Persona (con manejo de errores)
    @PostMapping("/persons")
    public String savePerson(@ModelAttribute Person person, Principal principal,
                              Model model) {
        Vigilant vigilant = vigilantService.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Vigilante no encontrado"));


        // Verificar si la identificación ya existe
        if (vehicleService.existsByIdentification(person.getIdentification())) {
            model.addAttribute("error", "La identificación ya está registrada");
            model.addAttribute("vigilant", vigilant);
            model.addAttribute("person", person);
            return "visitors-view/person-form";
        }

        // guarda si esta todo esta bien
        person.setVigilant(vigilant);
        person.setCreatedAt(new Date());
        person.setUpdatedAt(new Date());
        person.setActive(true);
        personService.save(person);

        return "redirect:/vigilant/persons?success=Persona registrada exitosamente";
    }

    // Mostrar formulario de edición
    @GetMapping("/persons/edit/{id}")
    public String showEditPersonForm(@PathVariable Long id, Model model, Principal principal) {
        Person person = personService.getById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrado"));

        Vigilant vigilant = vigilantService.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Vigilante no encontrado"));

        model.addAttribute("vigilant", vigilant);
        model.addAttribute("person", person);
        return "visitors-view/person-form";
    }

    // Eliminar Persona
    @PostMapping("/persons/delete/{id}")
    public String deletePerson(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            personService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Persona eliminada exitosamente");
            return "redirect:/vigilant/persons";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la persona");
            return "redirect:/vigilant/persons";
        }
    }


}