package org.sid.supplierservice.Repository;

import org.sid.supplierservice.Model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface SupplierRepository extends JpaRepository<Supplier,Long> {

}
