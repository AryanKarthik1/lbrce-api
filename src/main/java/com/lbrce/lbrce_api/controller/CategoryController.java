package com.lbrce.lbrce_api.controller;

import com.lbrce.lbrce_api.dto.CategoryCreateRequest;
import com.lbrce.lbrce_api.dto.CategoryResponse;
import com.lbrce.lbrce_api.dto.CategoryUpdateRequest;
import com.lbrce.lbrce_api.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    /* Create */
    @PostMapping
    public ResponseEntity<CategoryResponse> create(@RequestBody CategoryCreateRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    /* Read */
    @GetMapping("/{id}")
    public CategoryResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    /* List */
    @GetMapping
    public Page<CategoryResponse> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return service.list(pageable);
    }

    /* Update */
    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable UUID id, @RequestBody CategoryUpdateRequest req) {
        return service.update(id, req);
    }

    /* Delete */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    /* Exception handler */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
