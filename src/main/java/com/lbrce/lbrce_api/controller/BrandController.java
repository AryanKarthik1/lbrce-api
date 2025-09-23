package com.lbrce.lbrce_api.controller;

import com.lbrce.lbrce_api.dto.BrandCreateRequest;
import com.lbrce.lbrce_api.dto.BrandResponse;
import com.lbrce.lbrce_api.dto.BrandUpdateRequest;
import com.lbrce.lbrce_api.service.BrandService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

    private final BrandService service;
    public BrandController(BrandService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<BrandResponse> create(@RequestBody BrandCreateRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @GetMapping("/{id}")
    public BrandResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping
    public Page<BrandResponse> list(
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return service.list(q, pageable);
    }

    @PutMapping("/{id}")
    public BrandResponse update(@PathVariable UUID id, @RequestBody BrandUpdateRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
