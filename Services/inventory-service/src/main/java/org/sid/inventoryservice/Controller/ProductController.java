package org.sid.inventoryservice.Controller;

import org.sid.inventoryservice.Repository.ProductRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
