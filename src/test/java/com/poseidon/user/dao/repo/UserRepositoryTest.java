package com.poseidon.user.dao.repo;

import com.poseidon.user.dao.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.ZoneId;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @Test
    void testCreateDummyUser() {
        var user = getUser();
        var saved = repository.save(user);
        Assertions.assertTrue(saved.getId() > 1);
    }

    private User getUser() {
        var user = new User();
        user.setName("timmy");
        user.setEmail("timmy@timmy.com");
        user.setPassword("complicated");
        user.setRole("ADMIN");
        user.setCompanyCode("QE01");
        user.setEnabled(false);
        user.setCreatedBy("admin");
        user.setModifiedBy("admin");
        user.setCreatedOn(LocalDateTime.now(ZoneId.systemDefault()));
        user.setModifiedOn(LocalDateTime.now(ZoneId.systemDefault()));
        return user;
    }

}