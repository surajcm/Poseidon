package com.poseidon.user.service;

import com.poseidon.user.dao.UserDAO;
import com.poseidon.user.dao.entities.Role;
import com.poseidon.user.domain.UserVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserDetailsServiceImplTest {
    private final UserDAO userDAO = Mockito.mock(UserDAO.class);
    private final UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl(userDAO);

    @Test
    void loadUserByNullUsername() {
        when(userDAO.findByEmail(anyString())).thenReturn(null);
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("admin"));
    }

    @Test
    void loadUserByValidUsername() {
        var userName = "admin";
        when(userDAO.findByEmail(anyString())).thenReturn(mockUser());
        var userDetails = userDetailsService.loadUserByUsername("admin");
        Assertions.assertEquals(userName, userDetails.getUsername());
    }

    private UserVO mockUser() {
        var user = new UserVO();
        user.setName("admin");
        user.setPassword("pass");
        user.addRole(new Role(1L, "ADMIN"));
        return user;
    }
}