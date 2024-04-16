package com.poseidon.make.web.controller;

import com.poseidon.make.MakeConfigurations;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.service.MakeService;
import com.poseidon.model.entities.Model;
import com.poseidon.model.service.ModelService;
import com.poseidon.model.web.ModelController;
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
    @Autowired
    private ModelService modelService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(modelController).build();
    }

    @Test
    void modelList() throws Exception {
        when(modelService.listModels(anyInt())).thenReturn(mockModelsPage());
        mvc.perform(post("/make/ModelList")).andExpect(status().isOk());
        when(modelService.listAllMakesAndModels()).thenThrow(new RuntimeException());
        when(makeService.fetchAllMakes()).thenThrow(new RuntimeException());
        mvc.perform(post("/make/ModelList")).andExpect(status().isOk());
    }

    private Page<Model> mockModelsPage() {
        return new PageImpl<Model>(List.of(new Model(), new Model()));
    }

    @Test
    void testDeleteModel() throws Exception {
        when(modelService.listModels(anyInt())).thenReturn(mockModelsPage());
        mvc.perform(post("/make/deleteModel")).andExpect(status().isOk());
    }

    //@Test
    void testSearchModel() throws Exception {
        mvc.perform(post("/make/searchModel")).andExpect(status().isOk());
    }

    //@Test
    void saveModel() throws Exception {
        when(modelService.listAllMakesAndModels()).thenReturn(List.of(new MakeAndModelVO(), new MakeAndModelVO()));
        var selectMakeId = "1234";
        var selectModelName = "Mac book";
        mvc.perform(post("/make/saveModel")
                        .param("selectMakeId", selectMakeId)
                        .param("selectModelName", selectModelName))
                .andExpect(status().isOk());
    }
}