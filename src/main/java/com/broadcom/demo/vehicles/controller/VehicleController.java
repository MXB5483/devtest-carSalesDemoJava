package com.broadcom.demo.vehicles.controller;

import com.broadcom.demo.vehicles.model.Vehicle;
import com.broadcom.demo.vehicles.service.DynamicEndpointService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
public class VehicleController {

    private final DynamicEndpointService endpointService;
    private final RestTemplate restTemplate = new RestTemplate();

    public VehicleController(DynamicEndpointService endpointService) {
        this.endpointService = endpointService;
    }

    @GetMapping("/vehicles")
    public String list(Model model) {
        String baseUrl = endpointService.resolve("vehicles");
        System.out.println("🔗 UI fetching from API: " + baseUrl);

        try {
            ResponseEntity<Vehicle[]> response =
                restTemplate.getForEntity(baseUrl, Vehicle[].class);
            List<Vehicle> vehicles = Arrays.asList(Objects.requireNonNull(response.getBody()));
            model.addAttribute("vehicles", vehicles);
            model.addAttribute("total", vehicles.size());
            model.addAttribute("sold", vehicles.stream().filter(Vehicle::isSold).count());
        } catch (Exception e) {
            System.err.println("❌ Failed to fetch vehicles: " + e.getMessage());
            model.addAttribute("vehicles", List.of());
            model.addAttribute("total", 0);
            model.addAttribute("sold", 0);
        }

        model.addAttribute("activeEndpoint", baseUrl);
        return "vehicles";
    }

    @PostMapping("/vehicles/add")
    public String add(@RequestParam String brand,
                      @RequestParam String modelName,
                      @RequestParam int year,
                      @RequestParam BigDecimal price) {

        String baseUrl = endpointService.resolve("vehicles");
        Vehicle v = new Vehicle(null, brand, modelName, year, price, false,
                "https://source.unsplash.com/featured/?car");

        try {
            restTemplate.postForObject(baseUrl, v, Vehicle.class);
        } catch (Exception e) {
            System.err.println("❌ Failed to post to API: " + e.getMessage());
        }

        return "redirect:/vehicles";
    }

    @PostMapping("/vehicles/sell/{id}")
    public String sell(@PathVariable Long id) {
        String baseUrl = endpointService.resolve("vehicles") + "/" + id + "/sell";
        try {
            restTemplate.postForLocation(baseUrl, null);
        } catch (Exception e) {
            System.err.println("❌ Failed to sell vehicle via API: " + e.getMessage());
        }
        return "redirect:/vehicles";
    }
}
