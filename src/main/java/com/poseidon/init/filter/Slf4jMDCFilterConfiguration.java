package com.poseidon.init.filter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "summer.slf4jfilter")
public class Slf4jMDCFilterConfiguration {

    public static final String DEFAULT_RESPONSE_TOKEN_HEADER = "x-request-id";
    public static final String DEFAULT_MDC_UUID_TOKEN_KEY = "Slf4jMDCFilter.UUID";
    public static final String DEFAULT_MDC_CLIENT_IP_KEY = "Slf4jMDCFilter.ClientIP";

    private String responseHeader = DEFAULT_RESPONSE_TOKEN_HEADER;
    private String mdcTokenKey = DEFAULT_MDC_UUID_TOKEN_KEY;
    private String mdcClientIpKey = DEFAULT_MDC_CLIENT_IP_KEY;
    private String requestHeader = DEFAULT_RESPONSE_TOKEN_HEADER;

    @Bean
    public FilterRegistrationBean servletRegistrationBean() {
        final var registrationBean = new FilterRegistrationBean();
        final var log4jMDCFilterFilter =
                new Slf4jMDCFilter(responseHeader, mdcTokenKey, mdcClientIpKey, requestHeader);
        registrationBean.setFilter(log4jMDCFilterFilter);
        registrationBean.setOrder(2);
        return registrationBean;
    }
}