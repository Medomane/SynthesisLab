package org.sid.productservice;

import org.sid.productservice.Feign.SupplierRestClient;
import org.sid.productservice.Model.Product;
import org.sid.productservice.Repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
        /*List<String> prods = new ArrayList<>();
        prods.add("Lenovo");
        prods.add("HP");
        prods.add("Dell");
        prods.add("Apple");
        prods.add("Acer");*/


        var list = supplierRestClient.getSuppliers();
        var files = new File("C:\\Users\\Wizardly\\e-commerce\\products").list();
        Arrays.sort(files, Comparator.comparingInt(o -> Integer.parseInt(o.substring(0, o.indexOf("(")))));
        final int[] counter = {1};
        list.forEach(sup->{
            for (int i = counter[0]; i < counter[0] + 5; i++) {
                var price = Math.random() * (10000 - 1500 + 1) + 1500;
                var qte = Math.random() * (50 - 1 + 1) + 1;
                var s = files[i-1];
                s = s.substring(s.indexOf("(") + 1);
                s = s.substring(0, s.indexOf(")"));
                System.out.println(s);
                productRepository.save(new Product(null,s,Math.round(price),(int)qte,sup.getId(),sup));
            }
            counter[0] +=5;
        });
    }

}
