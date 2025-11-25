package com.poseidon.init.error;

import com.poseidon.init.ErrorConfigurations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PoseidonErrorController.class)
@ContextConfiguration(classes = {ErrorConfigurations.class})
class PoseidonErrorControllerTest {
    private MockMvc mvc;
    @Autowired
    private PoseidonErrorController errorController;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(errorController).build();
    }

    @Test
    void hitErrorEndpoints() throws Exception {
        mvc.perform(get("/error")).andExpect(status().isOk());
        mvc.perform(post("/error")).andExpect(status().isOk());
    }
}
