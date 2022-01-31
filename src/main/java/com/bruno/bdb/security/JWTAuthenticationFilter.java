package com.bruno.bdb.security;

import com.bruno.bdb.dto.CredentialsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@RequiredArgsConstructor
@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JWTUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            CredentialsDTO credentialsDTO = new ObjectMapper().readValue(request.getInputStream(), CredentialsDTO.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    credentialsDTO.getId(), credentialsDTO.getPassword(), new ArrayList<>()
            );
            return authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            log.info("Authentication Exception");
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.info("Attempt to login with a non-existing account number from '" + request.getLocalAddr() + "'.");
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        String id = ((SpringSecurityAccount) authResult.getPrincipal()).getUsername();
        String token = jwtUtil.generateToken(id);
        response.addHeader("Authorization", "Bearer " + token);
        log.info("Account '" + id + "' authenticated.");
    }
}