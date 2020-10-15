package com.poseidon.user.service.impl;

import com.poseidon.user.dao.UserDAO;
import com.poseidon.user.domain.UserVO;
import com.poseidon.user.exception.UserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserDetailsServiceImplTest {
    private final UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl();
    private final UserDAO userDAO = Mockito.mock(UserDAO.class);

    @BeforeEach
    public void setup() {
        Whitebox.setInternalState(userDetailsService, "userDAO", userDAO);
    }

    @Test
    void loadUserByNullUsername() throws UserException {
        when(userDAO.findByEmail(anyString())).thenReturn(null);
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("admin"));
    }

    @Test
    void loadUserByValidUsername() throws UserException {
        String userName = "admin";
        when(userDAO.findByEmail(anyString())).thenReturn(mockUser());
        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");
        Assertions.assertEquals(userName, userDetails.getUsername());
    }

    @Test
    void loadUserOnException() throws UserException {
        when(userDAO.findByEmail(anyString())).thenThrow(new UserException(UserException.DATABASE_ERROR));
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("admin"));
    }

    private UserVO mockUser() {
        UserVO user = new UserVO();
        user.setName("admin");
        user.setPassword("pass");
        user.setRole("admin");
        return user;
    }
}