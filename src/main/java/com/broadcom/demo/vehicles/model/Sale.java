package com.broadcom.demo.vehicles.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long vehicleId;      
    private String customerName;
    private LocalDate saleDate;

    public Sale() {}

    public Sale(Long id, Long vehicleId, String customerName, LocalDate saleDate) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.customerName = customerName;
        this.saleDate = saleDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getVehicleId() { return vehicleId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public LocalDate getSaleDate() { return saleDate; }
    public void setSaleDate(LocalDate saleDate) { this.saleDate = saleDate; }
}
