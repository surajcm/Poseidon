package com.poseidon.user.service;

import com.poseidon.user.dao.UserDAO;
import com.poseidon.user.domain.UserVO;
import com.poseidon.user.exception.UserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserServiceTest {
    private final UserDAO userDAO = Mockito.mock(UserDAO.class);
    private final BCryptPasswordEncoder bcryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
    private final UserService userService = new UserService(userDAO, bcryptPasswordEncoder);

    @Test
    void getAllUserDetailsSuccess() throws UserException {
        Assertions.assertNotNull(userService.getAllUserDetails());
    }

    @Test
    void getAllUserDetailsFailure() throws UserException {
        when(userDAO.getAllUserDetails()).thenThrow(new UserException(UserException.DATABASE_ERROR));
        Assertions.assertThrows(UserException.class, userService::getAllUserDetails);
    }

    @Test
    void saveSuccess() {
        Assertions.assertAll(() -> userService.save(new UserVO()));
    }

    @Test
    void saveFailure() throws UserException {
        doThrow(new UserException(UserException.DATABASE_ERROR)).when(userDAO).save(any());
        Assertions.assertThrows(UserException.class, () -> userService.save(new UserVO()));
    }

    @Test
    void getUserDetailsFromIdSuccess() throws UserException {
        when(userDAO.getUserDetailsFromId(anyLong())).thenReturn(new UserVO());
        Assertions.assertNotNull(userService.getUserDetailsFromId(1234L));
    }

    @Test
    void getUserDetailsFromIdFailure() throws UserException {
        when(userDAO.getUserDetailsFromId(anyLong())).thenThrow(new UserException(UserException.DATABASE_ERROR));
        Assertions.assertThrows(UserException.class, () -> userService.getUserDetailsFromId(1234L));
    }

    @Test
    void updateUserSuccess() {
        Assertions.assertAll(() -> userService.updateUser(new UserVO()));
    }

    @Test
    void updateUserFailure() throws UserException {
        doThrow(new UserException(UserException.DATABASE_ERROR)).when(userDAO).updateUser(any());
        Assertions.assertAll(() -> userService.updateUser(new UserVO()));
    }

    @Test
    void deleteUserSuccess() {
        Assertions.assertAll(() -> userService.deleteUser(1234L));
    }

    @Test
    void deleteUserFailure() throws UserException {
        doThrow(new UserException(UserException.DATABASE_ERROR)).when(userDAO).deleteUser(anyLong());
        Assertions.assertAll(() -> userService.deleteUser(1234L));
    }

    @Test
    void searchUserDetailsSuccess() throws UserException {
        Assertions.assertNotNull(userService.searchUserDetails(new UserVO()));
    }

    @Test
    void searchUserDetailsFailure() throws UserException {
        doThrow(new UserException(UserException.DATABASE_ERROR)).when(userDAO).searchUserDetails(any());
        Assertions.assertThrows(UserException.class, () -> userService.searchUserDetails(new UserVO()));
    }
}