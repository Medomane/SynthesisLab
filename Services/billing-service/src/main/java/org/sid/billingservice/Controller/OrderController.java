package org.sid.billingservice.Controller;

import org.sid.billingservice.Repository.OrderRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class OrderController {
    final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("countOrdersByCustomer/{id}")
    public long countOrdersByCustomer(@PathVariable Long id){
        return orderRepository.countOrdersByCustomer(id);
    }
}
