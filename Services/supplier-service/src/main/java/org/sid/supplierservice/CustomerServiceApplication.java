package org.sid.supplierservice;

import org.sid.supplierservice.Model.Supplier;
import org.sid.supplierservice.Repository.SupplierRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class CustomerServiceApplication implements CommandLineRunner {

    final SupplierRepository supplierRepository;
    final RepositoryRestConfiguration configuration;

    public CustomerServiceApplication(SupplierRepository supplierRepository, RepositoryRestConfiguration configuration) {
        this.supplierRepository = supplierRepository;
        this.configuration = configuration;
    }

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        configuration.exposeIdsFor(Supplier.class);
        for(var i=1;i<6;i++){
            supplierRepository.save(new Supplier(null,"Supplier"+i,"supplier"+i+"@gmail.com"));
        }
    }

}
