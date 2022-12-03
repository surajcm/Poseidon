package com.poseidon.user.web.controller;

import com.poseidon.user.UserConfigurations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {UserConfigurations.class})
class UserControllerTest {
    private MockMvc mvc;
    @Autowired
    private UserController userController;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void index() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk());
    }

    @Test
    @Disabled
    void listAll() throws Exception {
        mvc.perform(post("/user/listAll")).andExpect(status().isOk());
    }

    @Test
    @Disabled
    void deleteUser() throws Exception {
        mvc.perform(post("/user/deleteUser")).andExpect(status().isOk());
    }

    @Test
    void toHome() throws Exception {
        mvc.perform(post("/home")).andExpect(status().isOk());
    }

    @Test
    void logMeOut() throws Exception {
        mvc.perform(post("/logMeOut")).andExpect(status().isOk());
    }

    @Test
    void searchUser() throws Exception {
        mvc.perform(post("/user/searchUser")).andExpect(status().isOk());
    }

    @Test
    @Disabled
    void saveUser() throws Exception {
        String selectName = "Apple";
        String selectLogin = "apple";
        String selectRole = "admin";
        mvc.perform(post("/user/saveUser")
                        .param("selectName", selectName)
                        .param("selectLogin", selectLogin)
                        .param("selectRole", selectRole))
                .andExpect(status().isOk());
    }
}