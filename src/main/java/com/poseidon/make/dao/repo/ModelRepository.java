package com.poseidon.make.dao.repo;

import com.poseidon.make.dao.entities.Model;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends PagingAndSortingRepository<Model, Long> {
    List<Model> findByModelName(String modelName);

    @Query("Select m from Model m where m.modelName like :modelName")
    List<Model> findByModelNameWildCard(String modelName);
}
