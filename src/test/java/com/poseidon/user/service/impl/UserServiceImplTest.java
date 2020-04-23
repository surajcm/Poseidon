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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
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

    @Test
    public void saveSuccess() {
        Assertions.assertAll(() -> userService.save(new UserVO()));
    }

    @Test
    public void saveFailure() throws UserException {
        doThrow(new UserException(UserException.DATABASE_ERROR)).when(userDAO).save(any());
        Assertions.assertThrows(UserException.class, () -> userService.save(new UserVO()));
    }

    @Test
    public void getUserDetailsFromIdSuccess() throws UserException {
        when(userDAO.getUserDetailsFromId(anyLong())).thenReturn(new UserVO());
        Assertions.assertNotNull(userService.getUserDetailsFromId(1234L));
    }

    @Test
    public void getUserDetailsFromIdFailure() throws UserException {
        when(userDAO.getUserDetailsFromId(anyLong())).thenThrow(new UserException(UserException.DATABASE_ERROR));
        Assertions.assertThrows(UserException.class, () -> userService.getUserDetailsFromId(1234L));
    }

    @Test
    public void updateUserSuccess() {
        Assertions.assertAll(() -> userService.updateUser(new UserVO()));
    }

    @Test
    public void updateUserFailure() throws UserException {
        doThrow(new UserException(UserException.DATABASE_ERROR)).when(userDAO).updateUser(any());
        Assertions.assertAll(() -> userService.updateUser(new UserVO()));
    }

    @Test
    public void deleteUserSuccess() {
        Assertions.assertAll(() -> userService.deleteUser(1234L));
    }

    @Test
    public void deleteUserFailure() throws UserException {
        doThrow(new UserException(UserException.DATABASE_ERROR)).when(userDAO).deleteUser(anyLong());
        Assertions.assertAll(() -> userService.deleteUser(1234L));
    }

    @Test
    public void searchUserDetailsSuccess() throws UserException {
        Assertions.assertNotNull(userService.searchUserDetails(new UserVO()));
    }

    @Test
    public void searchUserDetailsFailure() throws UserException {
        doThrow(new UserException(UserException.DATABASE_ERROR)).when(userDAO).searchUserDetails(any());
        Assertions.assertThrows(UserException.class, () -> userService.searchUserDetails(new UserVO()));
    }
}