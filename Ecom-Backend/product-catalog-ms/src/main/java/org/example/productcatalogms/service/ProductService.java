package org.example.productcatalogms.service;

import lombok.AllArgsConstructor;
import org.example.productcatalogms.model.entity.Product;
import org.example.productcatalogms.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    ProductRepository productRepository;

    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Double getProductPrice(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        return (product != null) ? product.getPrice() : null;
    }
}
