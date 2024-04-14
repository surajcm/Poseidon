package com.poseidon.make.dao.repo;

import com.poseidon.make.dao.entities.Make;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MakeRepository extends PagingAndSortingRepository<Make, Long>, CrudRepository<Make, Long> {
    @Query("FROM Make m WHERE m.makeName LIKE :name%")
    List<Make> findByMakeName(@Param("name")String name);

}
