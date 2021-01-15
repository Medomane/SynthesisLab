package org.sid.billingservice.Repository;

import org.sid.billingservice.Model.BillLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BillLineRepository extends JpaRepository<BillLine,Long> {

}
