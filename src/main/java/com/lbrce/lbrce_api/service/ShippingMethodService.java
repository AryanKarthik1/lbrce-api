package com.lbrce.lbrce_api.service;

import com.lbrce.lbrce_api.dto.ShippingMethodRequest;
import com.lbrce.lbrce_api.dto.ShippingMethodResponse;
import com.lbrce.lbrce_api.model.ShippingMethod;
import com.lbrce.lbrce_api.repository.ShippingMethodRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ShippingMethodService {

    private final ShippingMethodRepository repo;

    public ShippingMethodService(ShippingMethodRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public ShippingMethodResponse create(ShippingMethodRequest r) {
        ShippingMethod sm = new ShippingMethod();
        sm.setName(r.name());
        sm.setDescription(r.description());
        sm.setStatus(r.status());
        sm = repo.save(sm);
        return toResponse(sm);
    }

    @Transactional(readOnly = true)
    public ShippingMethodResponse get(UUID id) {
        return repo.findById(id).map(this::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("ShippingMethod not found"));
    }

    @Transactional(readOnly = true)
    public List<ShippingMethodResponse> list() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional
    public ShippingMethodResponse update(UUID id, ShippingMethodRequest r) {
        ShippingMethod sm = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ShippingMethod not found"));
        if (r.name() != null) sm.setName(r.name());
        if (r.description() != null) sm.setDescription(r.description());
        if (r.status() != null) sm.setStatus(r.status());
        sm = repo.save(sm);
        return toResponse(sm);
    }

    @Transactional
    public void delete(UUID id) {
        repo.deleteById(id);
    }

    private ShippingMethodResponse toResponse(ShippingMethod sm) {
        return new ShippingMethodResponse(sm.getShippingMethodId(), sm.getName(), sm.getDescription(), sm.getStatus());
    }
}
