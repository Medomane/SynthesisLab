package org.sid.customerservice;

import org.sid.customerservice.Repository.CustomerRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CustomerServiceApplication {

    final CustomerRepository customerRepository;

    public CustomerServiceApplication(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    /*@Override
    public void run(String... args) {
        configuration.exposeIdsFor(Customer.class);
        customerRepository.save(new Customer(null,"Mohamed","med@gmail.com"));
        customerRepository.save(new Customer(null,"Gueddi","gueddi@gmail.com"));
        customerRepository.save(new Customer(null,"Anssari","anssari@gmail.com"));
        customerRepository.save(new Customer(null,"Aymane","aymane@gmail.com"));
        customerRepository.save(new Customer(null,"Yasser","yasser@gmail.com"));
    }*/

}
