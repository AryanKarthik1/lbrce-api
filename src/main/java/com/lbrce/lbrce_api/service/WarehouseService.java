package com.lbrce.lbrce_api.service;

import com.lbrce.lbrce_api.dto.*;
import com.lbrce.lbrce_api.model.Warehouse;
import com.lbrce.lbrce_api.repository.WarehouseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class WarehouseService {

    private final WarehouseRepository repo;

    public WarehouseService(WarehouseRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public WarehouseResponse create(WarehouseCreateRequest r) {
        Warehouse w = new Warehouse();
        w.setName(r.name());
        w.setAddress(r.address());
        w.setCity(r.city());
        w.setState(r.state());
        w.setPostalCode(r.postalCode());
        w.setStatus(r.status());
        return toResponse(repo.save(w));
    }

    @Transactional(readOnly = true)
    public WarehouseResponse get(UUID id) {
        Warehouse w = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Warehouse not found"));
        return toResponse(w);
    }

    @Transactional(readOnly = true)
    public List<WarehouseResponse> list() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional
    public WarehouseResponse update(UUID id, WarehouseUpdateRequest r) {
        Warehouse w = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Warehouse not found"));
        if (r.name() != null) w.setName(r.name());
        if (r.address() != null) w.setAddress(r.address());
        if (r.city() != null) w.setCity(r.city());
        if (r.state() != null) w.setState(r.state());
        if (r.postalCode() != null) w.setPostalCode(r.postalCode());
        if (r.status() != null) w.setStatus(r.status());
        return toResponse(repo.save(w));
    }

    @Transactional
    public void delete(UUID id) {
        repo.deleteById(id);
    }

    private WarehouseResponse toResponse(Warehouse w) {
        return new WarehouseResponse(w.getWarehouseId(), w.getName(), w.getAddress(), w.getCity(),
                w.getState(), w.getPostalCode(), w.getStatus());
    }
}
