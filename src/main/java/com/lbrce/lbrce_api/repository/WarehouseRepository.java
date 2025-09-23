package com.lbrce.lbrce_api.repository;

import com.lbrce.lbrce_api.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WarehouseRepository extends JpaRepository<Warehouse, UUID> {
}
