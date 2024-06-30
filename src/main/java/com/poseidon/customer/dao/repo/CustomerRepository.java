package com.poseidon.customer.dao.repo;

import com.poseidon.customer.dao.entities.Customer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long>,
        CrudRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    List<Customer> findByName(String name);
}
