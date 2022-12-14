package com.webapp.vascomm.service;

import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
public class logbook {

    // @Bean
    // public CommonsRequestLoggingFilter logFilter() {
    //     CommonsRequestLoggingFilter filter
    //       = new CommonsRequestLoggingFilter();
    //     filter.setIncludeQueryString(true);
    //     filter.setIncludePayload(true);
    //     filter.setMaxPayloadLength(10000);
    //     filter.setIncludeHeaders(true);
    //     filter.setAfterMessagePrefix("REQUEST DATA : ");
    //     return filter;
    // }


    @Bean
    public ServletRegistrationBean dispatcherRegistration() {
        return new ServletRegistrationBean(dispatcherServlet());
    }

    @Bean(name = DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
    public DispatcherServlet dispatcherServlet() {
        return new LoggableDispatcherServlet();
    }
}
