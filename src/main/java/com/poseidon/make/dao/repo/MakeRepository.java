package com.poseidon.make.dao.repo;

import com.poseidon.make.dao.entities.Make;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MakeRepository extends PagingAndSortingRepository<Make, Long> {
}
