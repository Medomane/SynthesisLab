package org.sid.productservice.Feign;

import org.sid.productservice.Model.Supplier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

@FeignClient(name = "SUPPLIER-SERVICE")
public interface SupplierRestClient {
    @GetMapping(path = "/suppliers/{id}")
    Supplier getSupplierById(@PathVariable(name = "id") Long id);
    @GetMapping(path = "/suppliers")
    Collection<Supplier> getSuppliers();
}
