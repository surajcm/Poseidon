package com.poseidon.customer.dao.impl;

import com.poseidon.customer.dao.entities.CustomerAdditionalDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAdditionalDetailsRepository extends JpaRepository<CustomerAdditionalDetails, Long> {

}
