package org.sid.secservice.SecuringWeb;

import org.sid.secservice.Filters.JwtAuthenticationFilter;
import org.sid.secservice.Filters.JwtAuthorizationFilter;
import org.sid.secservice.Repository.AppUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AppUserRepository appUserRepository;
    public SecurityConfig(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> {
            var user = appUserRepository.findAppUserByUsername(username);
            Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            user.getRoles().forEach(r -> grantedAuthorities.add(new SimpleGrantedAuthority(r.getRole())));
            return new User(user.getUsername(),user.getPassword(),grantedAuthorities);
        });
    }
    protected void configure(HttpSecurity http) throws Exception {
        //http.authorizeRequests().antMatchers(HttpMethod.POST,"/users/**").hasAuthority("ADMIN");
        //http.authorizeRequests().antMatchers(HttpMethod.GET,"/users/**").hasAuthority("USER");
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/refreshToken/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        //http.formLogin();
        http.addFilter(new JwtAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)  ;
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
