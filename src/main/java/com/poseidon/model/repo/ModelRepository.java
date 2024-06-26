package com.poseidon.model.repo;

import com.poseidon.model.entities.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends PagingAndSortingRepository<Model, Long>, CrudRepository<Model, Long> {
    List<Model> findByModelName(String modelName);

    @Query("Select m from Model m where m.modelName like :modelName")
    List<Model> findByModelNameWildCard(String modelName);

    Page<Model> findByMakeId(Long makeId, PageRequest pageable);

    Page<Model> findByMakeIdAndModelNameContaining(Long makeId, String name, PageRequest pageable);
}
