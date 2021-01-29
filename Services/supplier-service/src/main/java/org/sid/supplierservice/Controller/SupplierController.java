package org.sid.supplierservice.Controller;

import org.sid.supplierservice.Model.Supplier;
import org.sid.supplierservice.Repository.SupplierRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
public class SupplierController {
    final SupplierRepository supplierRepository;

    public SupplierController(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @GetMapping("/getSuppliers")
    public List<Supplier> getAll(){
        return supplierRepository.findAll();
    }
}
