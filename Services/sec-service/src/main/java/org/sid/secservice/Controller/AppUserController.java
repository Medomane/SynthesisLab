package org.sid.secservice.Controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sid.secservice.JwtUtil;
import org.sid.secservice.Model.AppRole;
import org.sid.secservice.Model.AppUser;
import org.sid.secservice.Repository.AppUserRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class AppUserController {
    final AppUserRepository appUserRepository;

    public AppUserController(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @GetMapping("/users")
    @PostAuthorize("hasAuthority('USER')")
    public List<AppUser> appUsers(){
        return appUserRepository.findAll();
    }
    @PostMapping(path = "/users")
    @PostAuthorize("hasAuthority('ADMIN')")
    public AppUser saveAppUser(@RequestBody AppUser user){
        return user.Save(appUserRepository);
    }
    @GetMapping("/refreshToken")
    public void refresh(HttpServletResponse response, HttpServletRequest request) {
        var header = request.getHeader(JwtUtil.AUTH_HEADER);
        if(header != null && header.startsWith(JwtUtil.PREFIX)){
            try{
                var jwt = header.substring(JwtUtil.PREFIX.length());
                Algorithm algorithm = Algorithm.HMAC256(JwtUtil.SECRET);
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT verify = verifier.verify(jwt);
                var user = appUserRepository.findAppUserByUsername(verify.getSubject());
                String jwtAccessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+JwtUtil.EXPIRE_ACCESS_TOKEN))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",user.getRoles().stream().map(AppRole::getRole).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String,String> idToken = new HashMap<>();
                idToken.put("access-token",jwtAccessToken);
                idToken.put("refresh-token",jwt);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(),idToken);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        else throw new RuntimeException("Refresh token required !!!");
    }
    @GetMapping("/profile")
    @PostAuthorize("hasAuthority('USER')")
    public AppUser profile(Principal principal){
        return appUserRepository.findAppUserByUsername(principal.getName());
    }
}