package org.sid.billingservice.Controller;

import org.sid.billingservice.Feign.CustomerRestClient;
import org.sid.billingservice.Feign.ProductRestClient;
import org.sid.billingservice.Model.Bill;
import org.sid.billingservice.Repository.BillRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@CrossOrigin("*")
@RestController
public class BillController {
    final BillRepository billRepository;
    final CustomerRestClient customerRestClient;
    final ProductRestClient productRestClient;

    public BillController(BillRepository billRepository, CustomerRestClient customerRestClient, ProductRestClient productRestClient) {
        this.billRepository = billRepository;
        this.customerRestClient = customerRestClient;
        this.productRestClient = productRestClient;
    }
    @GetMapping(path = "/bill/{id}")
    public Bill getBill(@PathVariable Long id){
        Bill bill = billRepository.findById(id).get();
        bill.setCustomer(customerRestClient.getCustomerById(bill.getCustomerId()));
        bill.getOrders().forEach(bl ->{
            bl.setProduct(productRestClient.getProductById(bl.getProductId()));
        });
        return bill;
    }
    @GetMapping(path = "/billsByDays/{userId}")
    public Collection<Object> getBillsByDays(@PathVariable Long userId){
        return billRepository.findBillsByDay(userId);
    }
    @GetMapping(path = "/countBillsByCustomer/{id}")
    public Object countBillsByCustomer(@PathVariable Long id){
        return billRepository.findBillsByCustomerId(id).size();
    }
    @GetMapping(path = "/billsByCustomer/{id}")
    public Collection<Bill> billsByCustomer(@PathVariable Long id){
        var list = billRepository.findBillsByCustomerId(id);
        list.forEach(b->{
            b.getOrders().forEach(order -> {
                order.setProduct(productRestClient.getProductById(order.getProductId()));
            });
        });
        return list;
    }
}
