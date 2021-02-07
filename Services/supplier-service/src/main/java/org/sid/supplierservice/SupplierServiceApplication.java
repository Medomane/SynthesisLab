package org.sid.supplierservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SupplierServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupplierServiceApplication.class, args);
    }

    /*@Override
    public void run(String... args) {
        for(var i=1;i<6;i++){
            supplierRepository.save(new Supplier(null,"Supplier"+i,"supplier"+i+"@gmail.com"));
        }
    }*/

}
