package com.alxtek.productservice.service;

import com.alxtek.productservice.dto.ProductDtoRequest;
import com.alxtek.productservice.dto.ProductDtoResponse;
import com.alxtek.productservice.model.Product;
import com.alxtek.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Slf4j
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public void createProduct(ProductDtoRequest productDtoRequest) {
        Product product = Product.builder()
                .name(productDtoRequest.getName())
                .description(productDtoRequest.getDescription())
                .price(productDtoRequest.getPrice())
                .build();
        log.info("Product saved: {}", productRepository.save(product));
    }

    public List<ProductDtoResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> ProductDtoResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build()).collect(Collectors.toList());
    }
}
