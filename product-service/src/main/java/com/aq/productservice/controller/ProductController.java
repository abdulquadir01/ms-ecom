package com.aq.productservice.controller;

import com.aq.productservice.dto.ProductRequest;
import com.aq.productservice.dto.ProductResponse;
import com.aq.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductRequest productRequest) {
        String savedProductId = productService.createProduct(productRequest);
        return new ResponseEntity<>("Product saved with ID: " + savedProductId, HttpStatus.CREATED);
    }

}
