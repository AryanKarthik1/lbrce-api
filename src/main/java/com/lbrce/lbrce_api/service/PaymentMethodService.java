package com.lbrce.lbrce_api.service;

import com.lbrce.lbrce_api.dto.PaymentMethodRequest;
import com.lbrce.lbrce_api.dto.PaymentMethodResponse;
import com.lbrce.lbrce_api.model.PaymentMethod;
import com.lbrce.lbrce_api.repository.PaymentMethodRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentMethodService {

    private final PaymentMethodRepository repo;

    public PaymentMethodService(PaymentMethodRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public PaymentMethodResponse create(PaymentMethodRequest req) {
        if (repo.existsBySlug(req.slug())) throw new IllegalArgumentException("Slug already exists");
        if (repo.existsByName(req.name())) throw new IllegalArgumentException("Name already exists");

        PaymentMethod pm = new PaymentMethod();
        pm.setName(req.name());
        pm.setSlug(req.slug());
        pm.setDescription(req.description());
        pm.setStatus(req.status() != null ? req.status() : "active");

        pm = repo.save(pm);
        return toResponse(pm);
    }

    @Transactional(readOnly = true)
    public List<PaymentMethodResponse> list() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public PaymentMethodResponse get(UUID id) {
        PaymentMethod pm = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found"));
        return toResponse(pm);
    }

    @Transactional
    public PaymentMethodResponse update(UUID id, PaymentMethodRequest req) {
        PaymentMethod pm = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found"));

        if (req.name() != null) pm.setName(req.name());
        if (req.slug() != null) pm.setSlug(req.slug());
        if (req.description() != null) pm.setDescription(req.description());
        if (req.status() != null) pm.setStatus(req.status());

        return toResponse(repo.save(pm));
    }

    @Transactional
    public void delete(UUID id) {
        repo.deleteById(id);
    }

    private PaymentMethodResponse toResponse(PaymentMethod pm) {
        return new PaymentMethodResponse(
                pm.getPaymentMethodId(),
                pm.getName(),
                pm.getSlug(),
                pm.getDescription(),
                pm.getStatus()
        );
    }
}
