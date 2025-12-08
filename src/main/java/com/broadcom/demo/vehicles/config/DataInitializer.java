package com.broadcom.demo.vehicles.config;

import com.broadcom.demo.vehicles.model.Vehicle;
import com.broadcom.demo.vehicles.model.Sale;
import com.broadcom.demo.vehicles.repository.VehicleRepository;
import com.broadcom.demo.vehicles.repository.SaleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Loads initial demo data when the database is empty.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final VehicleRepository vehicleRepository;
    private final SaleRepository saleRepository;

    public DataInitializer(VehicleRepository vehicleRepository, SaleRepository saleRepository) {
        this.vehicleRepository = vehicleRepository;
        this.saleRepository = saleRepository;
    }

    @Override
    public void run(String... args) {
        if (vehicleRepository.count() == 0) {
            System.out.println("🟢 No vehicles found — inserting demo data...");

            Vehicle v1 = new Vehicle(null, "Toyota", "Corolla", 2020, new BigDecimal("85000.00"), false,
                    "https://cdn.motor1.com/images/mgl/0ANJj/s1/2020-toyota-corolla.jpg");
            Vehicle v2 = new Vehicle(null, "Honda", "Civic", 2019, new BigDecimal("78000.00"), false,
                    "https://cdn.motor1.com/images/mgl/YAAjJ/s1/2020-honda-civic.jpg");
            Vehicle v3 = new Vehicle(null, "Ford", "Focus", 2021, new BigDecimal("91000.00"), true,
                    "https://cdn.motor1.com/images/mgl/QAAnJ/s1/2021-ford-focus.jpg");

            vehicleRepository.save(v1);
            vehicleRepository.save(v2);
            vehicleRepository.save(v3);

            // ✅ Save a demo sale with correct argument types
            Sale s1 = new Sale(null, v1.getId(), "John Doe", LocalDate.now());
            saleRepository.save(s1);

            System.out.println("✅ Demo data successfully loaded.");
        } else {
            System.out.println("ℹ️ Database already contains vehicles, skipping demo data load.");
        }
    }
}
