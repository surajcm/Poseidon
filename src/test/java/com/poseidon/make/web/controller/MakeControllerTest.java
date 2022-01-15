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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
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
    void modelList() throws Exception {
        when(makeService.listAllMakesAndModels()).thenReturn(mockMakeAndModelVOs());
        mvc.perform(post("/make/ModelList")).andExpect(status().isOk());
        when(makeService.listAllMakesAndModels()).thenThrow(new RuntimeException());
        when(makeService.fetchMakes()).thenThrow(new RuntimeException());
        mvc.perform(post("/make/ModelList")).andExpect(status().isOk());
    }

    @Test
    void makeList() throws Exception {
        mvc.perform(post("/make/MakeList")).andExpect(status().isOk());
    }

    @Test
    void deleteMake() throws Exception {
        mvc.perform(post("/make/deleteMake")).andExpect(status().isOk());
        doThrow(new RuntimeException()).when(makeService).deleteMake(null);
        mvc.perform(post("/make/deleteMake")).andExpect(status().isOk());
    }

    @Test
    void testDeleteModel() throws Exception {
        mvc.perform(post("/make/deleteModel")).andExpect(status().isOk());
        doThrow(new RuntimeException()).when(makeService).deleteModel(null);
        mvc.perform(post("/make/deleteModel")).andExpect(status().isOk());
    }

    @Test
    void testSearchModel() throws Exception {
        mvc.perform(post("/make/searchModel")).andExpect(status().isOk());
        when(makeService.searchMakeVOs(any())).thenThrow(new RuntimeException());
        mvc.perform(post("/make/searchModel")).andExpect(status().isOk());
    }

    @Test
    void saveMake() throws Exception {
        var selectMakeName = "Apple";
        var selectMakeDesc = "Mac book";
        mvc.perform(post("/make/saveMake")
                .param("selectMakeName", selectMakeName)
                .param("selectMakeDesc", selectMakeDesc))
                .andExpect(status().isOk());
        doThrow(new RuntimeException()).when(makeService).addNewMake(any());
        mvc.perform(post("/make/saveMake")
                .param("selectMakeName", selectMakeName)
                .param("selectMakeDesc", selectMakeDesc))
                .andExpect(status().isOk());
    }

    @Test
    void saveModelAjax() throws Exception {
        var selectMakeId = "1234";
        var selectModelName = "Mac book";
        mvc.perform(post("/make/saveModelAjax")
                .param("selectMakeId", selectMakeId)
                .param("selectModelName", selectModelName))
                .andExpect(status().isOk());
    }

    private List<MakeAndModelVO> mockMakeAndModelVOs() {
        List<MakeAndModelVO> makeAndModelVOs = new ArrayList<>();
        makeAndModelVOs.add(new MakeAndModelVO());
        makeAndModelVOs.add(new MakeAndModelVO());
        return makeAndModelVOs;
    }
}