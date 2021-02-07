package org.sid.productservice.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sid.productservice.Feign.SupplierRestClient;
import org.sid.productservice.Model.Product;
import org.sid.productservice.Repository.ProductRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    @GetMapping(value = "/products/{id}/icon",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable Long id) throws Exception {
        return Files.readAllBytes(Paths.get(new File(productRepository.findById(id).get().getImagePath()).toURI()));
    }

    @GetMapping(value = "/products")
    public List<Product> get(){
        var list = productRepository.findAll();
        list.forEach(product -> product.setSupplier(supplierRestClient.getSupplierById(product.getSupplierId())));
        return list;
    }

    @GetMapping(value = "/products/{id}")
    public Product get(@PathVariable Long id){
        var tmp = productRepository.findById(id).get();
        tmp.setSupplier(supplierRestClient.getSupplierById(tmp.getSupplierId()));
        return tmp;
    }

    @PostMapping(value = "/products/save")
    public Product upload(@RequestParam String product ,@RequestParam(value = "image",required = false) MultipartFile file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Product obj = mapper.readValue(product, Product.class);
        obj.setSupplierId(obj.getSupplier().getId());
        var prod = productRepository.save(obj);
        prod.setSupplier(supplierRestClient.getSupplierById(obj.getSupplier().getId()));
        if(file != null) prod.saveFile(file);
        return prod;
    }
    @PostMapping(value = "/products/update")
    public Product save(@RequestBody Product product){
        product.setSupplier(supplierRestClient.getSupplierById(product.getSupplierId()));
        return productRepository.save(product);
    }
    @DeleteMapping(value = "/products/{id}")
    public void delete(@PathVariable Long id){
        productRepository.deleteById(id);
    }

    @GetMapping(value = "/products/count")
    public int count(){
        return productRepository.countAllByIdNotNull();
    }
}
