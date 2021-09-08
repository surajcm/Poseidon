package com.poseidon.user.dao;

import com.poseidon.user.dao.entities.User;
import com.poseidon.user.dao.repo.UserRepository;
import com.poseidon.user.domain.UserVO;
import com.poseidon.user.exception.UserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserDAOTest {
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final UserDAO userDAO = new UserDAO(userRepository);

    @Test
    void logInSuccess() throws UserException {
        var userVO = new UserVO();
        userVO.setPassword("PASS");
        userVO.setEmail("ABC");
        when(userRepository.findByEmail(anyString())).thenReturn(mockUser());
        var result = userDAO.logIn(userVO);
        Assertions.assertEquals("ABC", result.getName());
    }

    @Test
    void logInWithDBError() {
        var userVO = new UserVO();
        userVO.setPassword("PASS");
        userVO.setEmail("ABC");
        when(userRepository.findByEmail(anyString())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(UserException.class, () -> userDAO.logIn(userVO));
    }

    @Test
    void logInWithIncorrectPassword() {
        var userVO = new UserVO();
        userVO.setPassword("PASS1");
        userVO.setEmail("ABC");
        when(userRepository.findByEmail(anyString())).thenReturn(mockUser());
        Assertions.assertThrows(UserException.class, () -> userDAO.logIn(userVO));
    }

    @Test
    void logInWithUnknownUser() {
        var userVO = new UserVO();
        userVO.setPassword("PASS1");
        userVO.setEmail("ABC");
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        Assertions.assertThrows(UserException.class, () -> userDAO.logIn(userVO));
    }

    @Test
    void getAllUserDetailsSuccess() throws UserException {
        when(userRepository.findAll()).thenReturn(mockUsers());
        Assertions.assertTrue(userDAO.getAllUserDetails().size() > 0);
    }

    @Test
    void getAllUserDetailsFailure() {
        when(userRepository.findAll()).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(UserException.class, userDAO::getAllUserDetails);
    }

    @Test
    void saveSuccess() {
        Assertions.assertAll(() -> userDAO.save(new UserVO()));
    }

    @Test
    void saveFailure() {
        when(userRepository.save(any())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(UserException.class, () -> userDAO.save(new UserVO()));
    }

    @Test
    void getUserDetailsFromIdSuccess() throws UserException {
        when(userRepository.findById(anyLong())).thenReturn(mockOptionalUser());
        Assertions.assertNotNull(userDAO.getUserDetailsFromId(1234L));
    }

    @Test
    void getUserDetailsFromIdSuccessOnEmpty() throws UserException {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertNull(userDAO.getUserDetailsFromId(1234L));
    }

    @Test
    void getUserDetailsFromIdFailure() {
        when(userRepository.findById(anyLong())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(UserException.class, () -> userDAO.getUserDetailsFromId(1234L));
    }

    @Test
    void updateEmpty() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        var vo = new UserVO();
        vo.setId(1234L);
        Assertions.assertAll(() -> userDAO.updateUser(vo));
    }

    @Test
    void updateSuccess() {
        when(userRepository.findById(anyLong())).thenReturn(mockOptionalUser());
        var vo = new UserVO();
        vo.setId(1234L);
        Assertions.assertAll(() -> userDAO.updateUser(vo));
    }

    @Test
    void updateFailure() {
        when(userRepository.findById(anyLong())).thenThrow(new CannotAcquireLockException("DB error"));
        var vo = new UserVO();
        vo.setId(1234L);
        Assertions.assertThrows(UserException.class, () -> userDAO.updateUser(vo));
    }

    @Test
    void deleteUserSuccess() {
        Assertions.assertAll(() -> userDAO.deleteUser(1234L));
    }

    @Test
    void deleteUserFailure() {
        doThrow(new CannotAcquireLockException("DB error")).when(userRepository).deleteById(anyLong());
        Assertions.assertThrows(UserException.class, () -> userDAO.deleteUser(1234L));
    }

    @Test
    void findByUsernameSuccess() throws UserException {
        when(userRepository.findByEmail(anyString())).thenReturn(mockUser());
        Assertions.assertNotNull(userDAO.findByEmail("ABC"));
    }

    @Test
    void findByUsernameFailure() {
        when(userRepository.findByEmail(anyString())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(UserException.class, () -> userDAO.findByEmail("ABC"));
    }

    @Test
    void searchUserDetailsSuccess() throws UserException {
        when(userRepository.findAll(any())).thenReturn(mockUsers());
        var user = mockUserVO();
        Assertions.assertNotNull(userDAO.searchUserDetails(user));
        user.setIncludes(Boolean.TRUE);
        Assertions.assertNotNull(userDAO.searchUserDetails(user));
        user.setIncludes(Boolean.FALSE);
        user.setStartsWith(Boolean.FALSE);
        Assertions.assertNotNull(userDAO.searchUserDetails(user));
        user.setStartsWith(Boolean.TRUE);
        Assertions.assertNotNull(userDAO.searchUserDetails(user));
    }

    @Test
    void searchUserDetailsSuccessOnNull() throws UserException {
        var user = mockUserVO();
        user.setName(null);
        user.setEmail(null);
        user.setRole(null);
        Assertions.assertNotNull(userDAO.searchUserDetails(user));
    }

    @Test
    void searchUserDetailsFailure() {
        when(userRepository.findAll(any())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(UserException.class, () -> userDAO.searchUserDetails(mockUserVO()));
    }

    private Optional<User> mockOptionalUser() {
        return Optional.of(mockUser());
    }

    private List<User> mockUsers() {
        return List.of(mockUser());
    }

    private User mockUser() {
        var user = new User();
        user.setUserId(1234L);
        user.setName("ABC");
        user.setEmail("ABC");
        user.setPassword("PASS");
        user.setRole("ADMIN");
        user.setCreatedBy("ADMIN");
        user.setModifiedBy("ADMIN");
        return user;
    }

    private UserVO mockUserVO() {
        var user = new UserVO();
        user.setId(1234L);
        user.setName("ABC");
        user.setEmail("ABC");
        user.setPassword("PASS");
        user.setRole("ADMIN");
        user.setCreatedBy("ADMIN");
        user.setLastModifiedBy("ADMIN");
        return user;
    }

}