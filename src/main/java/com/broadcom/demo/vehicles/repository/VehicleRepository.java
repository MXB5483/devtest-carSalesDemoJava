package com.broadcom.demo.vehicles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.broadcom.demo.vehicles.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {}