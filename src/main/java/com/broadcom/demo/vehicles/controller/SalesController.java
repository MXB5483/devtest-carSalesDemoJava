package com.broadcom.demo.vehicles.controller;

import com.broadcom.demo.vehicles.model.Sale;
import com.broadcom.demo.vehicles.model.Vehicle;
import com.broadcom.demo.vehicles.service.DynamicEndpointService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
public class SalesController {

    private final DynamicEndpointService endpointService;
    private final RestTemplate restTemplate = new RestTemplate();

    public SalesController(DynamicEndpointService endpointService) {
        this.endpointService = endpointService;
    }

    @GetMapping("/sales")
    public String list(Model model) {
        String baseUrl = endpointService.resolve("sales");
        System.out.println("🔗 Fetching sales from: " + baseUrl);

        try {
            ResponseEntity<Sale[]> response = restTemplate.getForEntity(baseUrl, Sale[].class);
            List<Sale> sales = Arrays.asList(Objects.requireNonNull(response.getBody()));
            model.addAttribute("sales", sales);
            model.addAttribute("total", sales.size());
        } catch (Exception e) {
            System.err.println("❌ Failed to fetch sales: " + e.getMessage());
            model.addAttribute("sales", List.of());
            model.addAttribute("total", 0);
        }

         try {
        String vehiclesUrl = endpointService.resolve("vehicles") + "/available";
        System.out.println("🔗 Fetching vechiles from: " + vehiclesUrl);
        ResponseEntity<Vehicle[]> response = restTemplate.getForEntity(vehiclesUrl, Vehicle[].class);
        List<Vehicle> vehicles = Arrays.asList(Objects.requireNonNull(response.getBody()));
        model.addAttribute("vehicles", vehicles);
    } catch (Exception e) {
        System.err.println("❌ Failed to fetch vehicles: " + e.getMessage());
        model.addAttribute("vehicles", List.of());
    }



        model.addAttribute("activeEndpoint", baseUrl);
        return "sales";
    }

    @PostMapping("/sales/add")
    public String add(@RequestParam Long vehicleId,
                      @RequestParam String customerName) {
        String baseUrl = endpointService.resolve("sales");
        Sale sale = new Sale(null, vehicleId, customerName, LocalDate.now());

        try {
            restTemplate.postForObject(baseUrl, sale, Sale.class);
        } catch (Exception e) {
            System.err.println("❌ Failed to post sale to API: " + e.getMessage());
        }

        return "redirect:/sales";
    }
}
