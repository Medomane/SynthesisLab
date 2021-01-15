package org.sid.secservice;

import org.sid.secservice.Model.AppRole;
import org.sid.secservice.Model.AppUser;
import org.sid.secservice.Repository.AppRoleRepository;
import org.sid.secservice.Repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.util.ArrayList;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SecServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(AppRoleRepository appRoleRepository, AppUserRepository appUserRepository){
        return args ->{
            var admin = appRoleRepository.save(new AppRole(null, "ADMIN"));
            var user = appRoleRepository.save(new AppRole(null, "USER"));
            var cm = appRoleRepository.save(new AppRole(null, "CUSTOMER_MANAGER"));
            var bm = appRoleRepository.save(new AppRole(null, "BILL_MANAGER"));
            var pm = appRoleRepository.save(new AppRole(null, "PRODUCT_MANAGER"));

            var med = new AppUser(null,"Med","1234", new ArrayList<>());
            med.getRoles().add(admin);
            med.getRoles().add(user);
            med.Save(appUserRepository);
            var gueddi = new AppUser(null,"Gueddi","1234", new ArrayList<>());
            gueddi.getRoles().add(user);
            gueddi.getRoles().add(pm);
            gueddi.Save(appUserRepository);
            var yasser = new AppUser(null,"Yasser","1234", new ArrayList<>());
            yasser.getRoles().add(cm);
            yasser.getRoles().add(user);
            yasser.Save(appUserRepository);
            var anssari = new AppUser(null,"Anssari","1234", new ArrayList<>());
            anssari.getRoles().add(pm);
            anssari.getRoles().add(user);
            anssari.Save(appUserRepository);
            var aymane = new AppUser(null,"Aymane","1234", new ArrayList<>());
            aymane.getRoles().add(bm);
            aymane.getRoles().add(user);
            aymane.Save(appUserRepository);
        };
    }
}
