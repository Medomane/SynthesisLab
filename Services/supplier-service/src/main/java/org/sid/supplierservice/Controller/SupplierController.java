package org.sid.supplierservice.Controller;

import org.sid.supplierservice.Model.Supplier;
import org.sid.supplierservice.Repository.SupplierRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class SupplierController {
    final SupplierRepository supplierRepository;

    public SupplierController(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @GetMapping("/suppliers")
    public List<Supplier> get(){
        return supplierRepository.findAll();
    }

    @GetMapping("/suppliers/{id}")
    public Supplier get(@PathVariable Long id){
        return supplierRepository.findById(id).get();
    }

    @PostMapping("/suppliers")
    public Supplier save(@RequestBody Supplier supplier){
        return supplierRepository.save(supplier);
    }

    @DeleteMapping("/suppliers/{id}")
    public void delete(@PathVariable Long id){
        supplierRepository.deleteById(id);
    }
}
