package org.sid.productservice;

import org.sid.productservice.Feign.SupplierRestClient;
import org.sid.productservice.Model.Product;
import org.sid.productservice.Repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
@EnableFeignClients
public class ProductServiceApplication implements CommandLineRunner {

    final ProductRepository productRepository;
    final RepositoryRestConfiguration configuration;
    final SupplierRestClient supplierRestClient;

    public ProductServiceApplication(ProductRepository productRepository, RepositoryRestConfiguration configuration, SupplierRestClient supplierRestClient) {
        this.productRepository = productRepository;
        this.configuration = configuration;
        this.supplierRestClient = supplierRestClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        configuration.exposeIdsFor(Product.class);
        var sup1 = supplierRestClient.getSupplierById(1L);
        var sup2 = supplierRestClient.getSupplierById(2L);
        var sup3 = supplierRestClient.getSupplierById(3L);

        Product d = productRepository.save(new Product(null,"DELL",2000,10,sup1.getId(),sup1));
        System.out.println(d.toString());
        d = productRepository.save(new Product(null,"HP",8000,15,sup1.getId(),sup1));
        System.out.println(d.toString());
        d = productRepository.save(new Product(null,"ACER",3000,20,sup1.getId(),sup1));
        System.out.println(d.toString());
        d = productRepository.save(new Product(null,"MSI",15000,3,sup2.getId(),sup2));
        System.out.println(d.toString());
        d = productRepository.save(new Product(null,"MAC",20000,7,sup3.getId(),sup3));
        System.out.println(d.toString());
    }

}
