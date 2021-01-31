package org.sid.billingservice.Repository;

import org.sid.billingservice.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query(value = "select bl from Bill as b inner join Order bl on b.id = bl.bill.id WHERE b.customerId = ?1")
    Collection<Order> ordersByCustomer(Long id);
}
