package org.sid.secservice.Filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.sid.secservice.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if(httpServletRequest.getServletPath().equals("/refreshToken")){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return ;
        }
        var header = httpServletRequest.getHeader(JwtUtil.AUTH_HEADER);
        if(header != null && header.startsWith(JwtUtil.PREFIX)){
            try{
                var jwt = header.substring(JwtUtil.PREFIX.length());
                Algorithm algorithm = Algorithm.HMAC256(JwtUtil.SECRET);
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT verify = verifier.verify(jwt);
                var username = verify.getSubject();
                var roles = verify.getClaim("roles").asArray(String.class);
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                for(var r : roles){
                    authorities.add(new SimpleGrantedAuthority(r));
                }
                var token = new UsernamePasswordAuthenticationToken(username,null,authorities);
                SecurityContextHolder.getContext().setAuthentication(token);
                filterChain.doFilter(httpServletRequest,httpServletResponse);
            }
            catch(Exception ex){
                httpServletResponse.setHeader("error-message",ex.getMessage());
                httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
                ex.printStackTrace();
            }
        }
        else filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
