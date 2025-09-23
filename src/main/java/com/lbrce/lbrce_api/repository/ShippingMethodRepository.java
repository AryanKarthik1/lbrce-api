package com.lbrce.lbrce_api.repository;

import com.lbrce.lbrce_api.model.ShippingMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShippingMethodRepository extends JpaRepository<ShippingMethod, UUID> {}
