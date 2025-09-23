package com.lbrce.lbrce_api.controller;

import com.lbrce.lbrce_api.dto.PaymentMethodRequest;
import com.lbrce.lbrce_api.dto.PaymentMethodResponse;
import com.lbrce.lbrce_api.service.PaymentMethodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payment-methods")
public class PaymentMethodController {

    private final PaymentMethodService service;

    public PaymentMethodController(PaymentMethodService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PaymentMethodResponse> create(@RequestBody PaymentMethodRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @GetMapping
    public List<PaymentMethodResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public PaymentMethodResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PutMapping("/{id}")
    public PaymentMethodResponse update(@PathVariable UUID id, @RequestBody PaymentMethodRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
