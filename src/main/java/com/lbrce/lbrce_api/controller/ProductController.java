package com.lbrce.lbrce_api.controller;

import com.lbrce.lbrce_api.dto.ProductCreateRequest;
import com.lbrce.lbrce_api.dto.ProductResponse;
import com.lbrce.lbrce_api.dto.ProductUpdateRequest;
import com.lbrce.lbrce_api.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;
    public ProductController(ProductService service) { this.service = service; }

    /* Create */
    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody ProductCreateRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    /* Read (by id) */
    @GetMapping("/{id}")
    public ProductResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    /* List (pagination + simple search q=name/sku/slug) */
    @GetMapping
    public Page<ProductResponse> list(
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return service.list(q, pageable);
    }

    /* Update (partial) */
    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable UUID id, @RequestBody ProductUpdateRequest req) {
        return service.update(id, req);
    }

    /* Delete (soft) */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    /* Simple 400 for validation / not found */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
