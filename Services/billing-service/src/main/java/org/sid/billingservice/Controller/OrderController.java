package org.sid.billingservice.Controller;

import org.sid.billingservice.Feign.CustomerRestClient;
import org.sid.billingservice.Model.Bill;
import org.sid.billingservice.Model.Order;
import org.sid.billingservice.Repository.BillRepository;
import org.sid.billingservice.Repository.OrderRepository;
import org.sid.billingservice.Repository.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;

@RestController
@CrossOrigin("*")
public class OrderController {
    final OrderRepository orderRepository;
    final OrderService orderService;
    final CustomerRestClient customerRestClient;
    final BillRepository billRepository;

    public OrderController(OrderRepository orderRepository, OrderService orderService, CustomerRestClient customerRestClient, BillRepository billRepository) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
        this.customerRestClient = customerRestClient;
        this.billRepository = billRepository;
    }

    @GetMapping("countOrdersByCustomer/{id}")
    public long countOrdersByCustomer(@PathVariable Long id){
        return orderRepository.countOrdersByCustomer(id);
    }

    @PostMapping("buy/{id}")
    public void buy(@RequestBody Collection<Order> orders, @PathVariable Long id){
        var bill = new Bill();
        bill.setDate(new Date());
        bill.setCustomerId(id);
        bill.setCustomer(customerRestClient.getCustomerById(id));
        var b = billRepository.save(bill);
        orders.forEach(or -> {
            or.setProductId(or.getProduct().getId());
            or.setBill(b);
            orderService.Save(or);
        });
    }
}
