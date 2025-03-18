package com.NDF.InventoryManagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public Map<String, String> home() {
        return Map.of(
                "message", "Welcome to the Inventory Management System API.",
                "instructions", "Use /products to manage inventory."
        );
    }
}
