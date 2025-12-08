package com.broadcom.demo.vehicles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.broadcom.demo.vehicles.model.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {}