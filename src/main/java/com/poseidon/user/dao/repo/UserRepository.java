package com.poseidon.user.dao.repo;

import com.poseidon.user.dao.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>,
        JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);

    User findByName(String name);

    List<User> findByCompanyCode(String companyCode);

    @Query("UPDATE User u set u.enabled = ?2 where u.id = ?1 ")
    @Modifying
    void updateEnabledStatus(Long id, boolean enabled);
}
