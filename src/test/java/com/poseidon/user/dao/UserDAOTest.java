package com.poseidon.user.dao;

import com.poseidon.user.dao.entities.Role;
import com.poseidon.user.dao.entities.User;
import com.poseidon.user.dao.repo.UserRepository;
import com.poseidon.user.dao.spec.UserSpecification;
import com.poseidon.user.domain.UserVO;
import com.poseidon.user.exception.UserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(UserException.class, () -> userDAO.logIn(userVO));
    }

    @Test
    void getAllUserDetailsSuccess() {
        when(userRepository.findByCompanyCode(anyString())).thenReturn(mockUsers());
        Assertions.assertTrue(userDAO.getAllUserDetails("admin").size() > 0);
    }

    @Test
    void saveSuccess() {
        Assertions.assertAll(() -> userDAO.save(new UserVO(), "admin"));
    }

    @Test
    void getUserDetailsFromIdSuccess() {
        when(userRepository.findById(anyLong())).thenReturn(mockUser());
        Assertions.assertNotNull(userDAO.getUserDetailsFromId(1234L));
    }

    @Test
    void getUserDetailsFromIdSuccessOnEmpty() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertTrue(userDAO.getUserDetailsFromId(1234L).isEmpty());
    }

    @Test
    void updateEmpty() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        var vo = new UserVO();
        vo.setId(1234L);
        Assertions.assertAll(() -> userDAO.updateUser(vo, "admin"));
    }

    @Test
    void updateSuccess() {
        when(userRepository.findById(anyLong())).thenReturn(mockUser());
        var vo = new UserVO();
        vo.setId(1234L);
        Assertions.assertAll(() -> userDAO.updateUser(vo, "admin"));
    }

    @Test
    void deleteUserSuccess() {
        Assertions.assertAll(() -> userDAO.deleteUser(1234L));
    }

    @Test
    void findByUsernameSuccess() {
        when(userRepository.findByEmail(anyString())).thenReturn(mockUser());
        Assertions.assertNotNull(userDAO.findByEmail("ABC"));
    }

    @Test
    void searchUserDetailsSuccess() {
        when(userRepository.findAll(any(UserSpecification.class))).thenReturn(mockUsers());
        var user = mockUserVO();
        Assertions.assertNotNull(userDAO.searchUserDetails(user, false, false));
        Assertions.assertNotNull(userDAO.searchUserDetails(user, false, true));
        Assertions.assertNotNull(userDAO.searchUserDetails(user, false, false));
        Assertions.assertNotNull(userDAO.searchUserDetails(user, true, false));
    }

    @Test
    void searchUserDetailsSuccessOnNull() {
        var user = mockUserVO();
        user.setName(null);
        user.setEmail(null);
        Assertions.assertNotNull(userDAO.searchUserDetails(user, false, false));
    }


    private List<User> mockUsers() {
        return List.of(mockUser().get());
    }

    private Optional<User> mockUser() {
        var user = new User();
        user.setId(1234L);
        user.setName("ABC");
        user.setEmail("ABC");
        user.setPassword("PASS");
        //user.setRole("ADMIN");
        user.setCreatedBy("ADMIN");
        user.setModifiedBy("ADMIN");
        return Optional.of(user);
    }

    private UserVO mockUserVO() {
        var user = new UserVO();
        user.setId(1234L);
        user.setName("ABC");
        user.setEmail("ABC");
        user.setPassword("PASS");
        user.addRole(new Role(1L));
        return user;
    }

}