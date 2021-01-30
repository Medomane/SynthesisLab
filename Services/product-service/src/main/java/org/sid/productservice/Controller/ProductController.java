package org.sid.productservice.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sid.productservice.Feign.SupplierRestClient;
import org.sid.productservice.Model.Product;
import org.sid.productservice.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

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
    @PostMapping(value = "saveProduct")
    public Product upload(@RequestParam String product ,@RequestParam(value = "image",required = false) MultipartFile file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Product obj = mapper.readValue(product, Product.class);
        obj.setSupplierId(obj.getSupplier().getId());
        var prod = productRepository.save(obj);
        prod.setSupplier(supplierRestClient.getSupplierById(obj.getSupplier().getId()));
        if(file != null) saveFile(System.getProperty("user.home")+"/e-commerce/products", prod.getId()+".jpg", file);
        return prod;
    }
    @PostMapping(value = "save")
    public Product save(@RequestBody Product product){
        product.setSupplier(supplierRestClient.getSupplierById(product.getSupplierId()));
        return productRepository.save(product);
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

    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }
}
