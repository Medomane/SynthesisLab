package org.sid.secservice.Repository;

import org.sid.secservice.Model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    AppUser findAppUserByUsername(String username);
}
