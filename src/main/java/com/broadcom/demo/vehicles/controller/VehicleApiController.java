package com.broadcom.demo.vehicles.controller;

import com.broadcom.demo.vehicles.model.Vehicle;
import com.broadcom.demo.vehicles.repository.VehicleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleApiController {

    private final VehicleRepository repo;

    public VehicleApiController(VehicleRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Vehicle> getAll() {
        return repo.findAll();
    }

    @GetMapping("/available")
    public List<Vehicle> getAvailable() {
        return repo.findAll().stream()
            .filter(v -> !v.isSold())
            .toList();
}

    @PostMapping
    public Vehicle add(@RequestBody Vehicle vehicle) {
        return repo.save(vehicle);
    }

    @PostMapping("/{id}/sell")
    public ResponseEntity<String> sell(@PathVariable Long id) {
        return repo.findById(id)
                .map(v -> {
                    v.setSold(true);
                    repo.save(v);
                    return ResponseEntity.ok("Vehicle sold");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
