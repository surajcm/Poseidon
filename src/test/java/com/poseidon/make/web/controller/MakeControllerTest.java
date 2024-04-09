package com.poseidon.make.web.controller;

import com.poseidon.make.MakeConfigurations;
import com.poseidon.make.service.MakeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MakeController.class)
@ContextConfiguration(classes = {MakeConfigurations.class})
class MakeControllerTest {
    private MockMvc mvc;
    @Autowired
    private MakeController makeController;
    @Autowired
    private MakeService makeService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(makeController).build();
    }

    @Test
    void makeList() throws Exception {
        mvc.perform(post("/make/MakeList")).andExpect(status().isOk());
    }

    @Test
    void deleteMake() throws Exception {
        mvc.perform(get("/make/delete/2/")).andExpect(status().isFound());
    }

    @Test
    void saveMake() throws Exception {
        var selectMakeName = "Apple";
        var selectMakeDesc = "Mac book";
        mvc.perform(post("/make/saveMake")
                .param("selectMakeName", selectMakeName)
                .param("selectMakeDesc", selectMakeDesc))
                .andExpect(status().isOk());
        doThrow(new RuntimeException()).when(makeService).addNewMake(anyString(), anyString(), anyString());
        mvc.perform(post("/make/saveMake")
                .param("selectMakeName", selectMakeName)
                .param("selectMakeDesc", selectMakeDesc))
                .andExpect(status().isOk());
    }

    @Test
    void saveModel() throws Exception {
        var selectMakeId = "1234";
        var selectModelName = "Mac book";
        mvc.perform(post("/make/saveModel")
                .param("selectMakeId", selectMakeId)
                .param("selectModelName", selectModelName))
                .andExpect(status().isOk());
    }

}