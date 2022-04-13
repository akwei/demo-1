package com.github.akwei.demo1;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TokenFilter extends OncePerRequestFilter {

    private RequestMatcher requiresAuthenticationRequestMatcher;

    public TokenFilter(String[] authWhiteList) {
        if (authWhiteList != null) {
            List<RequestMatcher> requestMatchers = new ArrayList<>();
            for (String s : authWhiteList) {
                requestMatchers.add(new AntPathRequestMatcher(s));
            }
            this.requiresAuthenticationRequestMatcher = new OrRequestMatcher(requestMatchers);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (this.requiresAuthenticationRequestMatcher != null && this.requiresAuthenticationRequestMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            filterChain.doFilter(request, response);
            return;
        }
        Jwt jwt = (Jwt) authentication.getPrincipal();
        if (jwt == null) {
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println(authentication);
        System.out.println(jwt);
        filterChain.doFilter(request, response);
    }
}
