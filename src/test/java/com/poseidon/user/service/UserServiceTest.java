package com.poseidon.user.service;

import com.poseidon.user.dao.UserDAO;
import com.poseidon.user.dao.repo.RoleRepository;
import com.poseidon.user.domain.UserVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserServiceTest {
    private final UserDAO userDAO = Mockito.mock(UserDAO.class);
    private final RoleRepository roleRepository = Mockito.mock(RoleRepository.class);
    private final BCryptPasswordEncoder bcryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
    private final UserService userService = new UserService(userDAO, bcryptPasswordEncoder, roleRepository);

    @Test
    void getAllUserDetailsSuccess() {
        Assertions.assertNotNull(userService.getAllUserDetails("admin"));
    }

    @Test
    void saveSuccess() {
        Assertions.assertAll(() -> userService.save(new UserVO(), "admin"));
    }

    @Test
    void getUserDetailsFromIdSuccess() {
        when(userDAO.getUserDetailsFromId(anyLong())).thenReturn(Optional.of(new UserVO()));
        Assertions.assertNotNull(userService.getUserDetailsFromId(1234L));
    }

    @Test
    void updateUserSuccess() {
        Assertions.assertAll(() -> userService.updateUser(new UserVO(), "admin"));
    }

    @Test
    void deleteUserSuccess() {
        Assertions.assertAll(() -> userService.deleteUser(1234L));
    }

    @Test
    void searchUserDetailsSuccess() {
        Assertions.assertNotNull(userService.searchUserDetails(new UserVO(), false, false));
    }
}