package org.sid.billingservice;

import org.sid.billingservice.Feign.CustomerRestClient;
import org.sid.billingservice.Feign.ProductRestClient;
import org.sid.billingservice.Model.Bill;
import org.sid.billingservice.Model.BillLine;
import org.sid.billingservice.Model.Customer;
import org.sid.billingservice.Repository.BillLineRepository;
import org.sid.billingservice.Repository.BillRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.Date;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication{

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(RepositoryRestConfiguration configuration, CustomerRestClient customerRestClient, BillRepository billRepository, ProductRestClient productRestClient, BillLineRepository billLineRepository){
        return args ->{
            configuration.exposeIdsFor(Bill.class);
            configuration.exposeIdsFor(BillLine.class);
            Bill bill=new Bill();
            bill.setDate(new Date());
            Customer customer=customerRestClient.getCustomerById(1L);
            System.out.println(customer.toString());
            bill.setCustomerId(customer.getId());
            billRepository.save(bill);
            productRestClient.pageProducts().getContent().forEach(p->{
                billLineRepository.save(new BillLine(null,(int)(1+Math.random()*1000),p.getId(),p,bill));
            });
        };
    }
}
