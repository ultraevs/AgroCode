package com.agrocode.middleware;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class CookieMiddleware implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        Cookie[] cookies = httpRequest.getCookies();
        String token = null;

        // Проверка наличия куки "Authtoken"
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Authtoken".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token == null) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No Token");
            return;
        } else {
        }

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey("SECRET") 
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();
            if (email != null) {
                httpRequest.setAttribute("Email", email);
            } else {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
                return;
            }
        } catch (Exception e) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bad Token");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
