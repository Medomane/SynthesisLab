package org.sid.billingservice.Repository;

import org.sid.billingservice.Model.Bill;
import org.sid.billingservice.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Collection;


@RepositoryRestResource
@CrossOrigin("*")
public interface BillRepository extends JpaRepository<Bill,Long> {
    @Query(value = "select b.Date as date,sum(bl.quantity) as quantity " +
            "from Bill as b inner join Order as bl on b.id = bl.bill.id WHERE b.customerId = ?1 group by b.Date")
    Collection<Object> findBillsByDay(Long id);

    Collection<Bill> findBillsByCustomerId(Long id);
}
