package org.sid.supplierservice.Repository;

import org.sid.supplierservice.Model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier,Long> {

}
