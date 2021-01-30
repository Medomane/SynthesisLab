package org.sid.billingservice.Repository;

import org.sid.billingservice.Feign.ProductRestClient;
import org.sid.billingservice.Model.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    final OrderRepository orderRepository;
    final ProductRestClient productRestClient;

    public OrderService(OrderRepository orderRepository, ProductRestClient productRestClient) {
        this.orderRepository = orderRepository;
        this.productRestClient = productRestClient;
    }

    public Order Save(Order order){
        var ord = orderRepository.save(order);
        var prod = productRestClient.getProductById(ord.getProductId());
        prod.setQuantityAvailable(prod.getQuantityAvailable()-ord.getQuantity());
        productRestClient.saveProduct(prod);
        ord.setProduct(prod);
        return ord;
    }
}
