package com.agrocode.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.agrocode.middleware.CookieMiddleware;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<CookieMiddleware> cookieFilter() {
        FilterRegistrationBean<CookieMiddleware> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new CookieMiddleware());
        registrationBean.addUrlPatterns(
            "/v1/*");

        return registrationBean;
    }
}
