package com.alxtek.productservice.controller;

import com.alxtek.productservice.dto.ProductDtoRequest;
import com.alxtek.productservice.dto.ProductDtoResponse;
import com.alxtek.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductDtoRequest productDtoRequest) {
        productService.createProduct(productDtoRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDtoResponse> getAllProducts() {
        return productService.getAllProducts();
    }
}
