package com.poseidon.user.dao.repo;

import com.poseidon.user.dao.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>, CrudRepository<User, Long>,
        JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);

    User findByName(String name);

    List<User> findByCompanyCode(String companyCode);

    Page<User> findByCompanyCode(Pageable pageable, String companyCode);

    @Query("UPDATE User u set u.enabled = ?2 where u.id = ?1 ")
    @Modifying
    void updateEnabledStatus(Long id, boolean enabled);
}
