package com.poseidon.company.dao.repo;

import com.poseidon.company.dao.entities.CompanyTerms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyTerms, Long> {
    Optional<CompanyTerms> findFirstByOrderByIdAsc();

    Optional<CompanyTerms> findByCompanyCode(String companyCode);
}
