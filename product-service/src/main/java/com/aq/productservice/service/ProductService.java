package com.aq.productservice.service;

import com.aq.productservice.dto.ProductRequest;
import com.aq.productservice.dto.ProductResponse;
import com.aq.productservice.model.Product;
import com.aq.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public String createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
            .name(productRequest.getName())
            .description(productRequest.getDescription())
            .price(productRequest.getPrice())
            .build();

        Product savedProduct = productRepository.save(product);
        log.info("Product saved with id = {}", savedProduct.getId());
        return savedProduct.getId();
    }

    public List<ProductResponse> getAllProducts() {

        List<Product> products = productRepository.findAll();

        log.info("Fetching all products.");
        return products.stream()
            .map(this::mapToProductResponse)
            .toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .build();
    }

}
