package com.broadcom.demo.vehicles.controller;

import com.broadcom.demo.vehicles.model.Sale;
import com.broadcom.demo.vehicles.model.Vehicle;
import com.broadcom.demo.vehicles.repository.SaleRepository;
import com.broadcom.demo.vehicles.repository.VehicleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SalesApiController {

    private final SaleRepository saleRepo;
    private final VehicleRepository vehicleRepo;

    public SalesApiController(SaleRepository saleRepo, VehicleRepository vehicleRepo) {
        this.saleRepo = saleRepo;
        this.vehicleRepo = vehicleRepo;
    }

    @GetMapping
    public List<Sale> getAll() {
        return saleRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<Sale> add(@RequestBody Sale sale) {
        Vehicle v = vehicleRepo.findById(sale.getVehicleId()).orElse(null);
        if (v == null) {
            return ResponseEntity.badRequest().build();
        }
        v.setSold(true);
        vehicleRepo.save(v);
        sale.setSaleDate(LocalDate.now());
        Sale saved = saleRepo.save(sale);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (saleRepo.existsById(id)) {
            saleRepo.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
