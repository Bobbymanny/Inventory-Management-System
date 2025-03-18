package com.NDF.InventoryManagement.controller;

import com.NDF.InventoryManagement.model.Product;
import com.NDF.InventoryManagement.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Get product by ID or search by name
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name) {

        if (id != null) {
            Optional<Product> product = productService.getProductById(id);
            return product.map(value -> ResponseEntity.ok(List.of(value)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }

        List<Product> products = (name != null && !name.isEmpty())
                ? productService.getProductsByName(name)
                : productService.getAllProducts();

        return ResponseEntity.ok(products);
    }

    // Add a new product
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product savedProduct = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    // Delete a product
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id)
                ? ResponseEntity.ok("Product deleted successfully")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    }
}
