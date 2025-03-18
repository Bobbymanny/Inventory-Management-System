package com.NDF.InventoryManagement.service;

import com.NDF.InventoryManagement.model.Product;
import com.NDF.InventoryManagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.Objects;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public Product addProduct(Product product) {
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product cannot be null");
        }
        if (Objects.isNull(product.getName()) || product.getName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product name cannot be empty");
        }
        if (Objects.isNull(product.getPrice()) || product.getPrice() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product price must be greater than 0");
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product newProduct) {
        return productRepository.findById(id).map(product -> {
            if (!Objects.isNull(newProduct.getName()) && !newProduct.getName().trim().isEmpty()) {
                product.setName(newProduct.getName());
            }
            if (!Objects.isNull(newProduct.getDescription())) {
                product.setDescription(newProduct.getDescription());
            }
            if (!Objects.isNull(newProduct.getQuantity())) {
                product.setQuantity(newProduct.getQuantity());
            }
            if (!Objects.isNull(newProduct.getPrice()) && newProduct.getPrice() > 0) {
                product.setPrice(newProduct.getPrice()); 
            }
            return productRepository.save(product);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
    }
}
