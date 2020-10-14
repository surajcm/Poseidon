package com.poseidon.user.dao.impl;

import com.poseidon.user.dao.entities.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long>,
        JpaSpecificationExecutor<User> {

    User findByEmail(String email);

}
