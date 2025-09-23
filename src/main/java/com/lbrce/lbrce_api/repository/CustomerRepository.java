package com.lbrce.lbrce_api.repository;

import com.lbrce.lbrce_api.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    Optional<Customer> findByCustomerIdAndStatus(UUID id, String status);

    Page<Customer> findByStatus(String status, Pageable pageable);
}
