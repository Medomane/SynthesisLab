package org.sid.billingservice.Controller;

import org.sid.billingservice.Feign.CustomerRestClient;
import org.sid.billingservice.Feign.ProductRestClient;
import org.sid.billingservice.Model.Bill;
import org.sid.billingservice.Model.Order;
import org.sid.billingservice.Repository.BillRepository;
import org.sid.billingservice.Repository.OrderRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;

@RestController
@CrossOrigin("*")
public class OrderController {
    final OrderRepository orderRepository;
    final CustomerRestClient customerRestClient;
    final BillRepository billRepository;
    final ProductRestClient productRestClient;

    public OrderController(OrderRepository orderRepository, CustomerRestClient customerRestClient, BillRepository billRepository, ProductRestClient productRestClient) {
        this.orderRepository = orderRepository;
        this.customerRestClient = customerRestClient;
        this.billRepository = billRepository;
        this.productRestClient = productRestClient;
    }

    @GetMapping("orders/byCustomer/{customerId}")
    public Collection<Order> ordersByCustomer(@PathVariable Long customerId){
        var list = orderRepository.ordersByCustomer(customerId);
        list.forEach(order -> {
            order.setProduct(productRestClient.getProductById(order.getProductId()));
        });
        return list;
    }

    @PostMapping("orders/buy/{customerId}")
    public void buy(@RequestBody Collection<Order> orders, @PathVariable Long customerId){
        var bill = new Bill();
        bill.setDate(new Date());
        bill.setCustomerId(customerId);
        bill.setCustomer(customerRestClient.getCustomerById(customerId));
        var b = billRepository.save(bill);
        orders.forEach(or -> {
            or.setProductId(or.getProduct().getId());
            or.setBill(b);
            orderRepository.save(or);
        });
    }
}
