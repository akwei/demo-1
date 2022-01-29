package com.github.akwei.demo1;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public AuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
        this.setAuthenticationFailureHandler((request, response, exception) -> {
            ErrResponse errResponse = ErrResponse.builder()
                    .httpCode(HttpStatus.UNAUTHORIZED.value())
                    .httpMessage(exception.getMessage())
                    .build();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(JsonUtil.object2json(errResponse));
            response.getWriter().flush();
        });
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("begin AuthenticationFilter ... >>>>, uri:{}", request.getRequestURI());
        String AUTHORIZATION = "Authorization";
        String token = request.getHeader(AUTHORIZATION);
        if (StringUtils.isNotEmpty(token)) {
            token = StringUtils.removeStart(token, "Bearer").trim();
        }
        Authentication requestAuthentication = new UsernamePasswordAuthenticationToken(token, token);
        return getAuthenticationManager().authenticate(requestAuthentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }

}
