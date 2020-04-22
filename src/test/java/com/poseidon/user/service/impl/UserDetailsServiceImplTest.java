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
public class UserDetailsServiceImplTest {
    private final UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl();
    private final UserDAO userRepository = Mockito.mock(UserDAO.class);

    @BeforeEach
    public void setup() {
        Whitebox.setInternalState(userDetailsService, "userRepository", userRepository);
    }

    @Test
    public void loadUserByNullUsername() throws UserException {
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("admin"));
    }

    @Test
    public void loadUserByValidUsername() throws UserException {
        String userName = "admin";
        when(userRepository.findByUsername(anyString())).thenReturn(mockUser());
        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");
        Assertions.assertEquals(userName, userDetails.getUsername());
    }

    private UserVO mockUser() {
        UserVO user = new UserVO();
        user.setName("admin");
        user.setPassword("pass");
        user.setRole("admin");
        return user;
    }
}