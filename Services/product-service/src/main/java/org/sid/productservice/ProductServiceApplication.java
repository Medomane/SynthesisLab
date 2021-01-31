package org.sid.productservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    /*@Override
    public void run(String... args) {
        var list = supplierRestClient.getSuppliers();
        var files = new File("C:\\Users\\Wizardly\\e-commerce\\products").list();
        Arrays.sort(files, Comparator.comparingInt(o -> Integer.parseInt(o.substring(0, o.indexOf("(")))));
        final int[] counter = {1};
        list.forEach(sup->{
            for (int i = counter[0]; i < counter[0] + 5; i++) {
                var price = Math.random() * (10000 - 1500 + 1) + 1500;
                var qte = Math.random() * (50 - 1 + 1) + 1;
                var s = files[i-1];
                s = s.substring(s.indexOf("(") + 1);
                s = s.substring(0, s.indexOf(")"));
                System.out.println(s);
                productRepository.save(new Product(null,s,Math.round(price),(int)qte,sup.getId(),sup));
            }
            counter[0] +=5;
        });
    }*/

}
