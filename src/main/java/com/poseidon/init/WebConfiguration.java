package com.poseidon.init;

import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {
    @Bean
    ServletRegistrationBean h2servletRegistration() {
        var registrationBean = new ServletRegistrationBean(
                new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }
}
