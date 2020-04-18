package com.demo1.applesson1.configs;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class FilterThatCheckTokenOfEveryRequest extends GenericFilterBean {
    private AuthenticationManager authenticationManager;

    public FilterThatCheckTokenOfEveryRequest(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String token = httpServletRequest.getHeader("Authorization");
        if (token != null) {
            String decodedToken = Jwts.parser().setSigningKey("admin".getBytes())
                    .parseClaimsJws(token)
                    .getBody().getSubject();

            String[] split = decodedToken.split("-");
            String username = split[0];
            String password = split[1];
            Authentication authenticate = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
            if(authenticate.isAuthenticated()){
                SecurityContextHolder.getContext().setAuthentication(authenticate);
            }
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }
}
