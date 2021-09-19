package com.poseidon.make.dao.repo;

import com.poseidon.make.dao.entities.Make;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MakeRepository extends JpaRepository<Make, Long> {
    List<Make> findByMakeName(String makeName);
}
