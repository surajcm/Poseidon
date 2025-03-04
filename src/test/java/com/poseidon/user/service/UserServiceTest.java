package com.poseidon.user.service;

import com.poseidon.user.dao.UserDAO;
import com.poseidon.user.dao.entities.User;
import com.poseidon.user.dao.repo.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserServiceTest {
    private final UserDAO userDAO = Mockito.mock(UserDAO.class);
    private final RoleRepository roleRepository = Mockito.mock(RoleRepository.class);
    private final BCryptPasswordEncoder bcryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
    private final UserService userService = new UserService(userDAO, bcryptPasswordEncoder, roleRepository);

    @Test
    void allUserDetailsSuccess() {
        Assertions.assertNotNull(userService.getAllUserDetails("admin"),
                "User details should not be null");
    }

    @Test
    void allUsersOfACompany() {
        when(userDAO.getAllUserDetails(anyString())).thenReturn(users());
        Assertions.assertNotNull(userService.getAllUserDetails("admin"),
                "User details should not be null");
    }

    @Test
    void saveSuccess() {
        Assertions.assertAll(() -> userService.save(new User()));
    }

    @Test
    void userDetailsFromIdSuccess() {
        when(userDAO.getUserDetailsFromId(anyLong())).thenReturn(Optional.of(new User()));
        Assertions.assertNotNull(userService.getUserDetailsFromId(1234L), "User details should not be null");
    }

    @Test
    void updateUserSuccess() {
        Assertions.assertAll(() -> userService.updateUser(new User(), "admin"));
    }

    @Test
    void deleteUserSuccess() {
        Assertions.assertAll(() -> userService.deleteUser(1234L));
    }

    @Test
    void searchUserDetailsSuccess() {
        Assertions.assertNull(userService.searchUserDetails(new User(), false, false, 1),
                "User details should be null");
    }

    private Set<User> users() {
        Set<User> users = new HashSet<>();
        User user = new User();
        users.add(user);
        return users;
    }
}