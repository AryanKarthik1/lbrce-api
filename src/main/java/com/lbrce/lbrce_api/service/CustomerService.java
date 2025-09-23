package com.lbrce.lbrce_api.service;

import com.lbrce.lbrce_api.dto.CustomerCreateRequest;
import com.lbrce.lbrce_api.dto.CustomerResponse;
import com.lbrce.lbrce_api.dto.CustomerUpdateRequest;
import com.lbrce.lbrce_api.model.Customer;
import com.lbrce.lbrce_api.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) { this.repo = repo; }

    @Transactional
    public CustomerResponse create(CustomerCreateRequest r) {
        if (repo.existsByEmail(r.email())) throw new IllegalArgumentException("Email already exists");
        if (repo.existsByPhone(r.phone())) throw new IllegalArgumentException("Phone already exists");

        var now = OffsetDateTime.now(ZoneOffset.UTC);

        Customer c = new Customer();
        c.setFirstName(r.firstName());
        c.setLastName(r.lastName());
        c.setEmail(r.email());
        c.setPhone(r.phone());
        c.setStatus(r.status() == null ? "active" : r.status());
        c.setCreatedAt(now);
        c.setUpdatedAt(now);

        return toResponse(repo.save(c));
    }

    @Transactional(readOnly = true)
    public CustomerResponse get(UUID id) {
        Customer c = repo.findByCustomerIdAndStatus(id, "active")
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        return toResponse(c);
    }

    @Transactional(readOnly = true)
    public Page<CustomerResponse> list(Pageable pageable) {
        return repo.findByStatus("active", pageable).map(this::toResponse);
    }

    @Transactional
    public CustomerResponse update(UUID id, CustomerUpdateRequest r) {
        Customer c = repo.findByCustomerIdAndStatus(id, "active")
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        if (r.firstName() != null) c.setFirstName(r.firstName());
        if (r.lastName() != null)  c.setLastName(r.lastName());
        if (r.email() != null)     c.setEmail(r.email());
        if (r.phone() != null)     c.setPhone(r.phone());
        if (r.status() != null)    c.setStatus(r.status());

        c.setUpdatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        return toResponse(repo.save(c));
    }

    @Transactional
    public void delete(UUID id) {
        Customer c = repo.findByCustomerIdAndStatus(id, "active")
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        c.setStatus("inactive");
        c.setUpdatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        repo.save(c);
    }

    private CustomerResponse toResponse(Customer c) {
        return new CustomerResponse(
                c.getCustomerId(),
                c.getFirstName(),
                c.getLastName(),
                c.getEmail(),
                c.getPhone(),
                c.getStatus(),
                c.getCreatedAt(),
                c.getUpdatedAt()
        );
    }
}
