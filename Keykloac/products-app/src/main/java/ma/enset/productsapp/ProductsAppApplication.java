package ma.enset.productsapp;

import ma.enset.productsapp.models.Product;
import ma.enset.productsapp.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class ProductsAppApplication implements CommandLineRunner {

    final ProductRepository productRepository;
    final RepositoryRestConfiguration configuration;

    public ProductsAppApplication(ProductRepository productRepository, RepositoryRestConfiguration configuration) {
        this.productRepository = productRepository;
        this.configuration = configuration;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProductsAppApplication.class, args);
    }

    public void run(String... args) {
        configuration.exposeIdsFor(Product.class);
        Product d = productRepository.save(new Product(null,"DELL",2000,10));
        System.out.println(d.toString());
        d = productRepository.save(new Product(null,"HP",8000,15));
        System.out.println(d.toString());
        d = productRepository.save(new Product(null,"ACER",3000,20));
        System.out.println(d.toString());
        d = productRepository.save(new Product(null,"MSI",15000,3));
        System.out.println(d.toString());
        d = productRepository.save(new Product(null,"MAC",20000,7));
        System.out.println(d.toString());
    }

}
