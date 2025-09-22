package com.lbrce.lbrce_api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from LBRCE API!";
    }

    @GetMapping("/greet")
    public String greet(@RequestParam(defaultValue = "Guest") String name) {
        return "Hello, " + name + "!";
    }

    @GetMapping("/greet/{name}")
    public String greetPath(@PathVariable String name) {
        return "Hello, " + name + "!";
    }

}
