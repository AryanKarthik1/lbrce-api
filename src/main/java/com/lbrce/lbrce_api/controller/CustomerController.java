package com.lbrce.lbrce_api.controller;

import com.lbrce.lbrce_api.dto.CustomerCreateRequest;
import com.lbrce.lbrce_api.dto.CustomerResponse;
import com.lbrce.lbrce_api.dto.CustomerUpdateRequest;
import com.lbrce.lbrce_api.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService service;
    public CustomerController(CustomerService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@RequestBody CustomerCreateRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @GetMapping("/{id}")
    public CustomerResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping
    public Page<CustomerResponse> list(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        return service.list(PageRequest.of(page, size));
    }

    @PutMapping("/{id}")
    public CustomerResponse update(@PathVariable UUID id, @RequestBody CustomerUpdateRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
