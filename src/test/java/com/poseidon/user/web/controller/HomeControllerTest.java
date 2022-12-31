package com.poseidon.user.web.controller;

import com.poseidon.user.UserConfigurations;
import org.junit.jupiter.api.BeforeEach;
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
@WebMvcTest(HomeController.class)
@ContextConfiguration(classes = {UserConfigurations.class})
class HomeControllerTest {

    private MockMvc mvc;

    @Autowired
    private HomeController homeController;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

    @Test
    void home() throws Exception {
        mvc.perform(post("/home")).andExpect(status().isOk());
    }

    @Test
    void index() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk());
    }

    @Test
    void logMeOut() throws Exception {
        mvc.perform(post("/logMeOut")).andExpect(status().isOk());
    }

}
