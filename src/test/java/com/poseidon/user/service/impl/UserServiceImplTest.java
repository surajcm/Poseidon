package com.poseidon.user.service.impl;

import com.poseidon.user.dao.UserDAO;
import com.poseidon.user.exception.UserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {
    private final UserServiceImpl userService = new UserServiceImpl();
    private final UserDAO userDAO = Mockito.mock(UserDAO.class);
    private final BCryptPasswordEncoder bcryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);

    @BeforeEach
    public void setup() {
        Whitebox.setInternalState(userService, "userDAO", userDAO);
        Whitebox.setInternalState(userService, "bcryptPasswordEncoder", bcryptPasswordEncoder);
    }

    @Test
    public void getAllUserDetailsSuccess() throws UserException {
        Assertions.assertNotNull(userService.getAllUserDetails());
    }

    @Test
    public void getAllUserDetailsFailure() throws UserException {
        when(userDAO.getAllUserDetails()).thenThrow(new UserException(UserException.DATABASE_ERROR));
        Assertions.assertThrows(UserException.class, userService::getAllUserDetails);
    }
}