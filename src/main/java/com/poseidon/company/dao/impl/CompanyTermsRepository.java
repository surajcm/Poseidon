package com.poseidon.company.dao.impl;

import com.poseidon.company.dao.entities.CompanyTerms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyTermsRepository extends JpaRepository<CompanyTerms, Long> {
}
