package com.poseidon.make.web.controller;

import com.poseidon.make.MakeConfigurations;
import com.poseidon.make.domain.MakeAndModelVO;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ModelController.class)
@ContextConfiguration(classes = {MakeConfigurations.class})
class ModelControllerTest {
    private MockMvc mvc;
    @Autowired
    private ModelController modelController;
    @Autowired
    private MakeService makeService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(modelController).build();
    }

    @Test
    void modelList() throws Exception {
        when(makeService.listAllMakesAndModels()).thenReturn(mockMakeAndModelVOs());
        mvc.perform(post("/make/ModelList")).andExpect(status().isOk());
        when(makeService.listAllMakesAndModels()).thenThrow(new RuntimeException());
        when(makeService.fetchMakes()).thenThrow(new RuntimeException());
        mvc.perform(post("/make/ModelList")).andExpect(status().isOk());
    }

    @Test
    void testDeleteModel() throws Exception {
        mvc.perform(post("/make/deleteModel")).andExpect(status().isOk());
        doThrow(new RuntimeException()).when(makeService).deleteModel(null);
        mvc.perform(post("/make/deleteModel")).andExpect(status().isOk());
    }

    private List<MakeAndModelVO> mockMakeAndModelVOs() {
        List<MakeAndModelVO> makeAndModelVOs = new ArrayList<>();
        makeAndModelVOs.add(new MakeAndModelVO());
        makeAndModelVOs.add(new MakeAndModelVO());
        return makeAndModelVOs;
    }
}