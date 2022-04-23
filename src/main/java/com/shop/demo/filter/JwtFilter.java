package com.shop.demo.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.TextCodec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtFilter implements javax.servlet.Filter, SecretHolder {

    /**
     *
     */
    private static final int tokenIndex = 7;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String header = httpServletRequest.getHeader("authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            throw new ServletException("Missing or invalid Authorization header!\n");
        } else {
            try {
                String token = header.substring(tokenIndex);
                Claims claims = Jwts.parser().setSigningKey(TextCodec.BASE64.encode(jwtSecret)).parseClaimsJws(token).getBody();
                request.setAttribute("claims", claims);
            } catch (final SignatureException e) {
                throw new ServletException("Invalid Token!");
            }
        }
        chain.doFilter(request, response);
    }
}
