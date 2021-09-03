package com.poseidon.customer.service;

import com.poseidon.customer.dao.repo.CustomerAdditionalDetailsRepository;
import com.poseidon.customer.dao.CustomerDAO;
import com.poseidon.customer.dao.repo.CustomerRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.EntityManagerFactory;

@ContextConfiguration
public class CustomerServiceConfiguration {
    @Bean
    public CustomerDAO customerDAO() {
        return Mockito.mock(CustomerDAO.class);
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        return Mockito.mock(EntityManagerFactory.class);
    }

    @Bean
    public CustomerRepository customerRepository() {
        return Mockito.mock(CustomerRepository.class);
    }

    @Bean
    public CustomerAdditionalDetailsRepository customerAdditionalDetailsRepository() {
        return Mockito.mock(CustomerAdditionalDetailsRepository.class);
    }
}
