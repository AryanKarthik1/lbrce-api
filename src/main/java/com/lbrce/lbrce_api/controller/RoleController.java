package com.lbrce.lbrce_api.controller;

import com.lbrce.lbrce_api.dto.RoleCreateRequest;
import com.lbrce.lbrce_api.dto.RoleResponse;
import com.lbrce.lbrce_api.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService service;
    public RoleController(RoleService service) { this.service = service; }

    /* Create */
    @PostMapping
    public ResponseEntity<RoleResponse> create(@RequestBody RoleCreateRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    /* Get by ID */
    @GetMapping("/{id}")
    public RoleResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    /* List */
    @GetMapping
    public List<RoleResponse> list() {
        return service.list();
    }

    /* Update */
    @PutMapping("/{id}")
    public RoleResponse update(@PathVariable UUID id, @RequestBody RoleCreateRequest req) {
        return service.update(id, req);
    }

    /* Delete */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
