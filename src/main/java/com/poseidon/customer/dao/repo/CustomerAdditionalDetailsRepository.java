package com.poseidon.customer.dao.repo;

import com.poseidon.customer.dao.entities.CustomerAdditionalDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerAdditionalDetailsRepository extends JpaRepository<CustomerAdditionalDetails, Long> {
    Optional<CustomerAdditionalDetails> findByCustomerId(Long customerId);
}
