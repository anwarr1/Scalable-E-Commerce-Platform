package org.example.productcatalogms.controller;

import lombok.AllArgsConstructor;
import org.example.productcatalogms.model.entity.Product;
import org.example.productcatalogms.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {
    // Inject the ProductService here
    private final ProductService productService;


    @GetMapping
    public List<Product> getAllProducts() {
        return productService.listProducts();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @GetMapping("/{id}/price")
    public Double getProductPrice(@PathVariable("id") Long id) {
        return productService.getProductPrice(id);
    }
}
