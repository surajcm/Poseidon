package com.poseidon.customer.dao.repo;

import com.poseidon.customer.dao.entities.CustomerAdditionalDetails;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerAdditionalDetailsRepository extends
        PagingAndSortingRepository<CustomerAdditionalDetails, Long> {
    Optional<CustomerAdditionalDetails> findByCustomerId(Long customerId);
}
