package org.sid.billingservice.Controller;

import org.sid.billingservice.Feign.ProductRestClient;
import org.sid.billingservice.Model.Bill;
import org.sid.billingservice.Repository.BillRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@CrossOrigin("*")
public class BillController {
    final BillRepository billRepository;
    final ProductRestClient productRestClient;

    public BillController(BillRepository billRepository, ProductRestClient productRestClient) {
        this.billRepository = billRepository;
        this.productRestClient = productRestClient;
    }
    /*@GetMapping(path = "/bills")
    public Bill get(){
        var bills = billRepository.findAll();
        list.forEach(b->{
            b.getOrders().forEach(order -> {
                order.setProduct(productRestClient.getProductById(order.getProductId()));
            });
        });
        bill.setCustomer(customerRestClient.getCustomerById(bill.getCustomerId()));
        bill.getOrders().forEach(bl ->{
            bl.setProduct(productRestClient.getProductById(bl.getProductId()));
        });
        return bill;
    }
    @GetMapping(path = "/bills/{id}")
    public Bill get(@PathVariable Long id){
        Bill bill = billRepository.findById(id).get();
        bill.setCustomer(customerRestClient.getCustomerById(bill.getCustomerId()));
        bill.getOrders().forEach(order ->{
            order.setProduct(productRestClient.getProductById(order.getProductId()));
        });
        return bill;
    }*/
    @GetMapping(path = "/bills/byDays/{customerId}")
    public Collection<Object> getBillsByDays(@PathVariable Long customerId){
        return billRepository.findBillsByDay(customerId);
    }
    @GetMapping(path = "/bills/byCustomer/{customerId}")
    public Collection<Bill> billsByCustomer(@PathVariable Long customerId){
        var list = billRepository.findBillsByCustomerId(customerId);
        list.forEach(b->{
            b.getOrders().forEach(order -> {
                order.setProduct(productRestClient.getProductById(order.getProductId()));
            });
        });
        return list;
    }
}
