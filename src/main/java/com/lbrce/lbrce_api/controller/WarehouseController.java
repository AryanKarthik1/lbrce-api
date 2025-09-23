package com.lbrce.lbrce_api.controller;

import com.lbrce.lbrce_api.dto.*;
import com.lbrce.lbrce_api.service.WarehouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    private final WarehouseService service;

    public WarehouseController(WarehouseService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<WarehouseResponse> create(@RequestBody WarehouseCreateRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @GetMapping("/{id}")
    public WarehouseResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping
    public List<WarehouseResponse> list() {
        return service.list();
    }

    @PutMapping("/{id}")
    public WarehouseResponse update(@PathVariable UUID id, @RequestBody WarehouseUpdateRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
