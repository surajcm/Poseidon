package com.poseidon.user.dao.impl;

import com.poseidon.user.dao.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByLogInId(String logInId);
    User findByName(String logInInamed);
}
