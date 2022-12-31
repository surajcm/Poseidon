package com.poseidon.user.web.controller;

import com.poseidon.user.UserConfigurations;
import com.poseidon.user.dao.entities.Role;
import com.poseidon.user.dao.entities.User;
import com.poseidon.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {UserConfigurations.class})
class UserControllerTest {
    private MockMvc mvc;
    @Autowired
    private UserController userController;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(userController, "userService", userService);
        mvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void listAll() throws Exception {
        when(userService.findUserFromName(anyString())).thenReturn(mockUser());
        mvc.perform(post("/user/listAll")).andExpect(status().isOk());
    }

    private User mockUser() {
        var user = new User();
        user.setId(1234L);
        user.setName("ABC");
        user.setEmail("ABC");
        user.setPassword("PASS");
        user.addRole(new Role(1L));
        user.setCompanyCode("QC01");
        return user;
    }

    @Test
    void deleteUser() throws Exception {
        mvc.perform(get("/user/deleteUser/2")).andExpect(status().is(302));
    }

    @Test
    @Disabled
    void searchUser() throws Exception {
        mvc.perform(post("/user/searchUser")).andExpect(status().isOk());
    }

    @Test
    void saveUser() throws Exception {
        when(userService.findUserFromName(anyString())).thenReturn(mockUser());
        var selectName = "Apple";
        var selectLogin = "apple";
        var selectRole = "3";
        mvc.perform(post("/user/saveUser")
                        .param("selectName", selectName)
                        .param("selectLogin", selectLogin)
                        .param("selectRole", selectRole))
                .andExpect(status().isOk());
    }
}