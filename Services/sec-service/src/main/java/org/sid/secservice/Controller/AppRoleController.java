package org.sid.secservice.Controller;

import lombok.Data;
import org.sid.secservice.Model.AppRole;
import org.sid.secservice.Repository.AppRoleRepository;
import org.sid.secservice.Repository.AppUserRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppRoleController {

    private final AppRoleRepository appRoleRepository;
    private final AppUserRepository appUserRepository;

    public AppRoleController(AppRoleRepository appRoleRepository, AppUserRepository appUserRepository) {
        this.appRoleRepository = appRoleRepository;
        this.appUserRepository = appUserRepository;
    }

    @PostMapping("/roles")
    @PostAuthorize("hasAuthority('ADMIN')")
    public AppRole saveAppRole(@RequestBody AppRole role){
        return appRoleRepository.save(role);
    }
    @PostMapping("/addRoleToUser")
    @PostAuthorize("hasAuthority('ADMIN')")
    public void saveAppRole(@RequestBody RoleUserForm roleUserForm){
        var user = appUserRepository.findById(roleUserForm.getUserId()).get();
        user.getRoles().add(appRoleRepository.findById(roleUserForm.getRoleId()).get());
        appUserRepository.save(user);
    }

}

@Data
class RoleUserForm{
    private Long userId;
    private Long roleId;
}
