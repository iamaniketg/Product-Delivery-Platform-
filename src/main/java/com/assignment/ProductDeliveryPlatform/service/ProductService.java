package com.assignment.ProductDeliveryPlatform.service;

import com.assignment.ProductDeliveryPlatform.model.Product;
import com.assignment.ProductDeliveryPlatform.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public void removeProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> allProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(Long id, Product product) {
        Product product1 = productRepository.findFirstById(id);
        product1.setId(product.getId());
        product1.setName(product.getName());
        product1.setDescription(product.getDescription());
        product1.setPrice(product.getPrice());
        return productRepository.save(product1);

    }
}
