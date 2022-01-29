package com.github.akwei.demo1;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final JwtDecoder jwtDecoder;

    public AuthenticationProvider(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();
        if (StringUtils.isEmpty(token)) {
            throw new UsernameNotFoundException("token invalid");
        }
        try {
            Jwt jwt = this.jwtDecoder.decode(token);
            SessionUser sessionUser = new SessionUser();
            sessionUser.setUserId(jwt.getId());
            sessionUser.setName(jwt.getSubject());
            return sessionUser;
        } catch (JwtException e) {
            throw new UsernameNotFoundException("token invalid", e);
        }

    }
}
