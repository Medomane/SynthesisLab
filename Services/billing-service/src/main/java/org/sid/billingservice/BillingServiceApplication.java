package org.sid.billingservice;

import org.sid.billingservice.Feign.CustomerRestClient;
import org.sid.billingservice.Feign.ProductRestClient;
import org.sid.billingservice.Model.Bill;
import org.sid.billingservice.Model.Customer;
import org.sid.billingservice.Model.Order;
import org.sid.billingservice.Repository.OrderRepository;
import org.sid.billingservice.Repository.BillRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication{

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(RepositoryRestConfiguration configuration, CustomerRestClient customerRestClient, BillRepository billRepository, ProductRestClient productRestClient, OrderRepository orderRepository){
        return args ->{
            configuration.exposeIdsFor(Bill.class);
            configuration.exposeIdsFor(Order.class);
            Customer customer=customerRestClient.getCustomerById(1L);
            for (int i = 0; i < 3; i++) {
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                c.add(Calendar.DATE, -i-10);
                Bill bill=new Bill();
                bill.setDate(c.getTime());
                bill.setCustomerId(customer.getId());
                billRepository.save(bill);
                var max = Math.ceil(Math.random()*10)+5;
                for(var j=0;j<max;j++){
                    var prod = productRestClient.getProductById((long) Math.ceil(Math.random()*25));
                    var order = new Order();
                    order.setBill(bill);
                    order.setProductId(prod.getId());
                    order.setQuantity((int) Math.ceil(Math.random()*2));
                    orderRepository.save(order);
                }
            }
        };
    }
}
