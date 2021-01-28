package org.sid.productservice.Controller;

import org.sid.productservice.Feign.SupplierRestClient;
import org.sid.productservice.Model.Product;
import org.sid.productservice.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@CrossOrigin("*")
public class ProductController {
    private final ProductRepository productRepository;
    private final SupplierRestClient supplierRestClient;

    public ProductController(ProductRepository productRepository, SupplierRestClient supplierRestClient) {
        this.productRepository = productRepository;
        this.supplierRestClient = supplierRestClient;
    }

    @GetMapping(value = "/icon/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable Long id) throws Exception {
        return Files.readAllBytes(Paths.get(new File(System.getProperty("user.home")+"/e-commerce/products/"+id+".jpg").toURI()));
    }
    @GetMapping(value = "get/{id}")
    public Product getOne(@PathVariable Long id){
        var tmp = productRepository.findById(id).get();
        tmp.setSupplier(supplierRestClient.getSupplierById(tmp.getSupplierId()));
        return tmp;
    }
    @GetMapping(value = "get")
    public List<Product> getAll(){
        var list = productRepository.findAll();
        list.forEach(product -> product.setSupplier(supplierRestClient.getSupplierById(product.getSupplierId())));
        return list;
    }
}
