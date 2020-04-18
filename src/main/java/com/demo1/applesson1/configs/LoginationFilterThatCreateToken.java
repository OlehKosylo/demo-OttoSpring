package com.demo1.applesson1.configs;

import com.demo1.applesson1.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

//For single LOGINATION request, not for all request
public class LoginationFilterThatCreateToken extends AbstractAuthenticationProcessingFilter {
    private AuthenticationManager authenticationManager;
    private User temp;

    protected LoginationFilterThatCreateToken(String defaultFilterProcessesUrl, AuthenticationManager manager) {
        super(defaultFilterProcessesUrl);
        this.authenticationManager = manager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        User user = new ObjectMapper().readValue(httpServletRequest.getInputStream(), User.class);

        Authentication authenticate =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if (authenticate.isAuthenticated()) {
            temp = user;
        }
        return authenticate;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = temp.getUsername();
        String password = temp.getPassword();

        String token = Jwts.builder()
                .setSubject(username + '-' + password)
                .signWith(SignatureAlgorithm.HS512, "admin".getBytes())
                .compact();

        response.addHeader("sendBackToken",token);
    }
}
