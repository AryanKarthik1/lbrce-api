package com.lbrce.lbrce_api.repository;

import com.lbrce.lbrce_api.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, UUID> {
    boolean existsBySlug(String slug);
    boolean existsByName(String name);
}
