package com.sid.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sid.kafka.Core.MyGlobals;
import com.sid.kafka.Feign.CustomerRestClient;
import com.sid.kafka.Feign.ProductRestClient;
import com.sid.kafka.Model.Order;
import com.sid.kafka.Model.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@EnableFeignClients
@SpringBootApplication
public class KafkaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(KafkaTemplate<String, String> kafkaTemplate, CustomerRestClient customerRestClient, ProductRestClient productRestClient) {
        return args -> {
            var customers = customerRestClient.count();
            var products = productRestClient.count();
            Executors.newScheduledThreadPool(1).scheduleAtFixedRate(
                ()->{
                    var customerId = Math.ceil(Math.random()*customers);
                    int maxOrders = (int) Math.ceil(Math.random()*5);
                    var orders = new ArrayList<Order>();
                    for(var i=0;i<maxOrders;i++){
                        var prodId = Math.ceil(Math.random()*products);
                        var quantity = Math.ceil(Math.random()*10);
                        orders.add(getOrder(quantity,prodId));
                    }
                    try {
                        var order = new ObjectMapper().writeValueAsString(orders);
                        kafkaTemplate.send(MyGlobals.TOPIC,String.valueOf(customerId),order);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                },1,1, TimeUnit.SECONDS
            );
        };
    }
    static Order getOrder(double quantity,double productId){
        var prod = new Product();
        prod.setId((long) productId);
        return new Order(quantity,prod.getId(),prod);
    }
}
