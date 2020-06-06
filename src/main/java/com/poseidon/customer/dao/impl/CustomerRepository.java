package com.poseidon.customer.dao.impl;

import com.poseidon.customer.dao.entities.Customer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long>,
        JpaSpecificationExecutor<Customer> {
}
