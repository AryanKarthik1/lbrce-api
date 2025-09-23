package com.lbrce.lbrce_api.controller;

import com.lbrce.lbrce_api.dto.VendorCreateRequest;
import com.lbrce.lbrce_api.dto.VendorResponse;
import com.lbrce.lbrce_api.dto.VendorUpdateRequest;
import com.lbrce.lbrce_api.service.VendorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    private final VendorService service;
    public VendorController(VendorService service) { this.service = service; }

    /* Create */
    @PostMapping
    public ResponseEntity<VendorResponse> create(@RequestBody VendorCreateRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    /* Read (by id) */
    @GetMapping("/{id}")
    public VendorResponse get(@PathVariable UUID id) { return service.get(id); }

    /* List (pagination + ?q= search) */
    @GetMapping
    public Page<VendorResponse> list(
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return service.list(q, pageable);
    }

    /* Update */
    @PutMapping("/{id}")
    public VendorResponse update(@PathVariable UUID id, @RequestBody VendorUpdateRequest req) {
        return service.update(id, req);
    }

    /* Delete (soft -> status=suspended) */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) { service.delete(id); }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
