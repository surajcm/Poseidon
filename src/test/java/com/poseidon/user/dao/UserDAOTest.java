package com.poseidon.user.dao;

import com.poseidon.user.dao.entities.User;
import com.poseidon.user.dao.repo.UserRepository;
import com.poseidon.user.dao.spec.UserSpecification;
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
    void allUserDetailsSuccess() {
        when(userRepository.findByCompanyCode(anyString())).thenReturn(mockUsers());
        Assertions.assertTrue(userDAO.getAllUserDetails("admin").size() > 0);
    }

    @Test
    void saveSuccess() {
        Assertions.assertAll(() -> userDAO.save(new User()));
    }

    @Test
    void userDetailsFromIdSuccess() {
        when(userRepository.findById(anyLong())).thenReturn(mockUser());
        Assertions.assertNotNull(userDAO.getUserDetailsFromId(1234L));
    }

    @Test
    void userDetailsFromIdSuccessOnEmpty() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertTrue(userDAO.getUserDetailsFromId(1234L).isEmpty());
    }

    @Test
    void updateEmpty() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        var vo = new User();
        vo.setId(1234L);
        Assertions.assertAll(() -> userDAO.updateUser(vo, "admin"));
    }

    @Test
    void updateSuccess() {
        when(userRepository.findById(anyLong())).thenReturn(mockUser());
        var vo = new User();
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
        var user = mockUser().get();
        Assertions.assertNotNull(userDAO.searchUserDetails(user, false, false, 1));
        Assertions.assertNotNull(userDAO.searchUserDetails(user, false, true, 1));
        Assertions.assertNotNull(userDAO.searchUserDetails(user, false, false, 1));
        Assertions.assertNotNull(userDAO.searchUserDetails(user, true, false, 1));
    }

    @Test
    void searchUserDetailsSuccessOnNull() {
        var user = mockUser().get();
        user.setName(null);
        user.setEmail(null);
        Assertions.assertNotNull(userDAO.searchUserDetails(user, false, false, 1));
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

}