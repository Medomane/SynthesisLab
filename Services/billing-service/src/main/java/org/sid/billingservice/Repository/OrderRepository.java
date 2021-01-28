package org.sid.billingservice.Repository;

import org.sid.billingservice.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query(value = "select COUNT(bl.id) from Bill as b inner join Order bl on b.id = bl.bill.id WHERE b.customerId = ?1")
    Long countOrdersByCustomer(Long id);
}
