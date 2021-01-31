package org.sid.billingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication{

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    /*@Bean
    CommandLineRunner start(CustomerRestClient customerRestClient, BillRepository billRepository, ProductRestClient productRestClient, OrderRepository orderRepository){
        return args ->{
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
    }*/
}
