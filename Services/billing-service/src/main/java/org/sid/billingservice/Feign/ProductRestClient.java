package org.sid.billingservice.Feign;

import org.sid.billingservice.Model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "INVENTORY-SERVICE")
public interface ProductRestClient {
    @GetMapping(path = "/products")
    PagedModel<Product> pageProducts();

    @GetMapping(path = "/products/{id}")
    Product getProductById(@PathVariable(name = "id") Long id);
}
