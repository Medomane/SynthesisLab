package org.sid.customerservice.Controller;

import org.sid.customerservice.Model.Customer;
import org.sid.customerservice.Repository.CustomerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class CustomerController {
    final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers")
    public List<Customer> get(){
        return customerRepository.findAll();
    }

    @GetMapping("/customers/{id}")
    public Customer get(@PathVariable Long id){
        return customerRepository.findById(id).get();
    }

    @PostMapping("/customers")
    public Customer save(@RequestBody Customer supplier){
        return customerRepository.save(supplier);
    }

    @DeleteMapping("/customers/{id}")
    public void delete(@PathVariable Long id){
        customerRepository.deleteById(id);
    }
}
