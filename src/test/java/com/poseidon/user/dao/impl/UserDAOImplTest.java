package com.poseidon.user.dao.impl;

import com.poseidon.user.dao.entities.User;
import com.poseidon.user.domain.UserVO;
import com.poseidon.user.exception.UserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserDAOImplTest {
    private final UserDAOImpl userDAO = new UserDAOImpl();
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);

    @BeforeEach
    public void setup() {
        Whitebox.setInternalState(userDAO, "userRepository", userRepository);
    }

    @Test
    public void logInSuccess() throws UserException {
        UserVO userVO = new UserVO();
        userVO.setPassword("PASS");
        userVO.setLoginId("ABC");
        when(userRepository.findByLogInId(anyString())).thenReturn(mockUser());
        UserVO result = userDAO.logIn(userVO);
        Assertions.assertEquals("ABC", result.getName());
    }

    @Test
    public void logInWithDBError() {
        UserVO userVO = new UserVO();
        userVO.setPassword("PASS");
        userVO.setLoginId("ABC");
        when(userRepository.findByLogInId(anyString())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(UserException.class, () -> userDAO.logIn(userVO));
    }

    @Test
    public void logInWithIncorrectPassword() {
        UserVO userVO = new UserVO();
        userVO.setPassword("PASS1");
        userVO.setLoginId("ABC");
        when(userRepository.findByLogInId(anyString())).thenReturn(mockUser());
        Assertions.assertThrows(UserException.class, () -> userDAO.logIn(userVO));
    }

    @Test
    public void logInWithUnknownUser() {
        UserVO userVO = new UserVO();
        userVO.setPassword("PASS1");
        userVO.setLoginId("ABC");
        when(userRepository.findByLogInId(anyString())).thenReturn(null);
        Assertions.assertThrows(UserException.class, () -> userDAO.logIn(userVO));
    }

    @Test
    public void getAllUserDetailsSuccess() throws UserException {
        when(userRepository.findAll()).thenReturn(mockUsers());
        Assertions.assertTrue(userDAO.getAllUserDetails().size() > 0);
    }

    @Test
    public void getAllUserDetailsFailure() {
        when(userRepository.findAll()).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(UserException.class, userDAO::getAllUserDetails);
    }

    @Test
    public void saveSuccess() {
        Assertions.assertAll(() -> userDAO.save(new UserVO()));
    }

    @Test
    public void saveFailure() {
        when(userRepository.save(any())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(UserException.class, () -> userDAO.save(new UserVO()));
    }

    @Test
    public void getUserDetailsFromIdSuccess() throws UserException {
        when(userRepository.findById(anyLong())).thenReturn(mockOptionalUser());
        Assertions.assertNotNull(userDAO.getUserDetailsFromId(1234L));
    }

    @Test
    public void getUserDetailsFromIdSuccessOnEmpty() throws UserException {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertNull(userDAO.getUserDetailsFromId(1234L));
    }

    @Test
    public void getUserDetailsFromIdFailure() {
        when(userRepository.findById(anyLong())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(UserException.class, () -> userDAO.getUserDetailsFromId(1234L));
    }

    @Test
    public void updateEmpty() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        UserVO vo = new UserVO();
        vo.setId(1234L);
        Assertions.assertAll(() -> userDAO.updateUser(vo));
    }

    @Test
    public void updateSuccess() {
        when(userRepository.findById(anyLong())).thenReturn(mockOptionalUser());
        UserVO vo = new UserVO();
        vo.setId(1234L);
        Assertions.assertAll(() -> userDAO.updateUser(vo));
    }

    @Test
    public void updateFailure() {
        when(userRepository.findById(anyLong())).thenThrow(new CannotAcquireLockException("DB error"));
        UserVO vo = new UserVO();
        vo.setId(1234L);
        Assertions.assertThrows(UserException.class, () -> userDAO.updateUser(vo));
    }

    @Test
    public void deleteUserSuccess() {
        Assertions.assertAll(() -> userDAO.deleteUser(1234L));
    }

    @Test
    public void deleteUserFailure() {
        doThrow(new CannotAcquireLockException("DB error")).when(userRepository).deleteById(anyLong());
        Assertions.assertThrows(UserException.class, () -> userDAO.deleteUser(1234L));
    }

    @Test
    public void findByUsernameSuccess() throws UserException {
        when(userRepository.findByName(anyString())).thenReturn(mockUser());
        Assertions.assertNotNull(userDAO.findByUsername("ABC"));
    }

    @Test
    public void findByUsernameFailure() {
        when(userRepository.findByName(anyString())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(UserException.class, () -> userDAO.findByUsername("ABC"));
    }

    private Optional<User> mockOptionalUser() {
        return Optional.of(mockUser());
    }

    private List<User> mockUsers() {
        List<User> users = new ArrayList<>();
        users.add(mockUser());
        return users;
    }

    private User mockUser() {
        User user = new User();
        user.setUserId(1234L);
        user.setName("ABC");
        user.setLogInId("ABC");
        user.setPassword("PASS");
        user.setRole("ADMIN");
        user.setCreatedBy("ADMIN");
        user.setModifiedBy("ADMIN");
        return user;
    }

}