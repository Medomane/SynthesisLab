package org.sid.customerservice;

import org.sid.customerservice.Model.Customer;
import org.sid.customerservice.Repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class CustomerServiceApplication implements CommandLineRunner {

    final CustomerRepository customerRepository;
    final RepositoryRestConfiguration configuration;

    public CustomerServiceApplication(CustomerRepository customerRepository, RepositoryRestConfiguration configuration) {
        this.customerRepository = customerRepository;
        this.configuration = configuration;
    }

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        configuration.exposeIdsFor(Customer.class);
        customerRepository.save(new Customer(null,"Mohamed","med@gmail.com"));
        customerRepository.save(new Customer(null,"Gueddi","gueddi@gmail.com"));
        customerRepository.save(new Customer(null,"Anssari","anssari@gmail.com"));
        customerRepository.save(new Customer(null,"Aymane","aymane@gmail.com"));
        customerRepository.save(new Customer(null,"Yasser","yasser@gmail.com"));
    }

}
