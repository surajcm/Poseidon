package com.poseidon.make.web.controller;

import com.poseidon.make.MakeConfigurations;
import com.poseidon.make.dao.entities.Make;
import com.poseidon.make.service.MakeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
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
        when(makeService.listAll(anyInt())).thenReturn(mockPageOfMake());
        mvc.perform(post("/make/MakeList")).andExpect(status().isOk());
    }

    private Page<Make> mockPageOfMake() {
        return new PageImpl<Make>(List.of(new Make(), new Make()));
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
    }

}