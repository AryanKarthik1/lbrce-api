package com.lbrce.lbrce_api.service;

import com.lbrce.lbrce_api.dto.RoleCreateRequest;
import com.lbrce.lbrce_api.dto.RoleResponse;
import com.lbrce.lbrce_api.model.Role;
import com.lbrce.lbrce_api.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class RoleService {

    private final RoleRepository repo;

    public RoleService(RoleRepository repo) {
        this.repo = repo;
    }

    /* Create */
    @Transactional
    public RoleResponse create(RoleCreateRequest req) {
        if (repo.existsByName(req.name())) throw new IllegalArgumentException("Role name already exists");
        if (repo.existsBySlug(req.slug())) throw new IllegalArgumentException("Role slug already exists");

        Role r = new Role();
        r.setName(req.name());
        r.setSlug(req.slug());
        r.setDescription(req.description());
        r.setStatus(req.status() != null ? req.status() : "active");

        r = repo.save(r);
        return toResponse(r);
    }

    /* Get by ID */
    @Transactional(readOnly = true)
    public RoleResponse get(UUID id) {
        Role r = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Role not found"));
        return toResponse(r);
    }

    /* List all */
    @Transactional(readOnly = true)
    public List<RoleResponse> list() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    /* Update */
    @Transactional
    public RoleResponse update(UUID id, RoleCreateRequest req) {
        Role r = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Role not found"));

        if (req.name() != null) r.setName(req.name());
        if (req.slug() != null) r.setSlug(req.slug());
        if (req.description() != null) r.setDescription(req.description());
        if (req.status() != null) r.setStatus(req.status());

        r = repo.save(r);
        return toResponse(r);
    }

    /* Delete */
    @Transactional
    public void delete(UUID id) {
        Role r = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Role not found"));
        repo.delete(r);
    }

    private RoleResponse toResponse(Role r) {
        return new RoleResponse(r.getRoleId(), r.getName(), r.getSlug(), r.getDescription(), r.getStatus());
    }
}
