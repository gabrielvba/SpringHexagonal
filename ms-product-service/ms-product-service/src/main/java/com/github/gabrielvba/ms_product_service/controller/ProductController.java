package com.github.gabrielvba.ms_product_service.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.gabrielvba.ms_product_service.dto.ProductDTO;
import com.github.gabrielvba.ms_product_service.model.Product;
import com.github.gabrielvba.ms_product_service.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        log.info("Creating Product: {}", productDTO);
        var newProduct = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) throws Exception {
        log.info("Updating Product with id {}: {}", id, productDTO);
        var updatedProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.info("Deleting Product with id {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> listProducts() {
        log.info("Listing all products");
        var products = productService.listProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        log.info("Fetching Product with id {}", id);
        var product = productService.getProductById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> getProductsByIds(@RequestParam List<Long> ids) {
        log.info("Fetching Products with ids {}", ids);
        var products = productService.getProductsByIds(ids);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }
}