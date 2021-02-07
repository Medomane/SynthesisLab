package org.sid.productservice.Repository;

import org.sid.productservice.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product,Long> {
    int countAllByIdNotNull();
}
