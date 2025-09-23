package com.lbrce.lbrce_api.controller;

import com.lbrce.lbrce_api.dto.ShippingMethodRequest;
import com.lbrce.lbrce_api.dto.ShippingMethodResponse;
import com.lbrce.lbrce_api.service.ShippingMethodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/shipping-methods")
public class ShippingMethodController {

    private final ShippingMethodService service;

    public ShippingMethodController(ShippingMethodService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ShippingMethodResponse> create(@RequestBody ShippingMethodRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @GetMapping("/{id}")
    public ShippingMethodResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping
    public List<ShippingMethodResponse> list() {
        return service.list();
    }

    @PutMapping("/{id}")
    public ShippingMethodResponse update(@PathVariable UUID id, @RequestBody ShippingMethodRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
