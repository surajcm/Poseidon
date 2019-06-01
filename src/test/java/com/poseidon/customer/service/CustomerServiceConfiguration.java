package com.poseidon.customer.service;

import com.poseidon.customer.dao.impl.CustomerDAOImpl;
import com.poseidon.customer.dao.impl.CustomerRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.EntityManagerFactory;

@ContextConfiguration
public class CustomerServiceConfiguration {
    @Bean
    public CustomerDAOImpl customerDAO() {
        return Mockito.mock(CustomerDAOImpl.class);
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        return Mockito.mock(EntityManagerFactory.class);
    }

    @Bean
    public CustomerRepository customerRepository() {
        return Mockito.mock(CustomerRepository.class);
    }
}
