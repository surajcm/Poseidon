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
public class MakeControllerTest {
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
    public void modelList() throws Exception {
        when(makeService.listAllMakesAndModels()).thenReturn(mockMakeAndModelVOs());
        mvc.perform(post("/make/ModelList.htm")).andExpect(status().isOk());
        when(makeService.listAllMakesAndModels()).thenThrow(new RuntimeException());
        when(makeService.fetchMakes()).thenThrow(new RuntimeException());
        mvc.perform(post("/make/ModelList.htm")).andExpect(status().isOk());
    }

    private List<MakeAndModelVO> mockMakeAndModelVOs() {
        List<MakeAndModelVO> makeAndModelVOs = new ArrayList<>();
        makeAndModelVOs.add(new MakeAndModelVO());
        makeAndModelVOs.add(new MakeAndModelVO());
        return makeAndModelVOs;
    }

    @Test
    public void makeList() throws Exception {
        mvc.perform(post("/make/MakeList.htm")).andExpect(status().isOk());
    }

    @Test
    public void editMake() throws Exception {
        mvc.perform(post("/make/editMake.htm")).andExpect(status().isOk());
        when(makeService.getMakeFromId(null)).thenThrow(new RuntimeException());
        mvc.perform(post("/make/editMake.htm")).andExpect(status().isOk());
    }

    @Test
    public void deleteMake() throws Exception {
        mvc.perform(post("/make/deleteMake.htm")).andExpect(status().isOk());
        doThrow(new RuntimeException()).when(makeService).deleteMake(null);
        mvc.perform(post("/make/deleteMake.htm")).andExpect(status().isOk());
    }

    @Test
    public void editModel() throws Exception {
        mvc.perform(post("/make/editModel.htm")).andExpect(status().isOk());
        when(makeService.getModelFromId(null)).thenThrow(new RuntimeException());
        when(makeService.listAllMakes()).thenThrow(new RuntimeException());
        mvc.perform(post("/make/editModel.htm")).andExpect(status().isOk());
    }

    @Test
    public void testDeleteModel() throws Exception {
        mvc.perform(post("/make/deleteModel.htm")).andExpect(status().isOk());
        doThrow(new RuntimeException()).when(makeService).deleteModel(null);
        mvc.perform(post("/make/deleteModel.htm")).andExpect(status().isOk());
    }

    @Test
    public void testUpdateMake() throws Exception {
        mvc.perform(post("/make/updateMake.htm")).andExpect(status().isOk());
        doThrow(new RuntimeException()).when(makeService).updateMake(null);
        mvc.perform(post("/make/updateMake.htm")).andExpect(status().isOk());
    }

    @Test
    public void testUpdateModel() throws Exception {
        mvc.perform(post("/make/updateModel.htm")).andExpect(status().isOk());
        doThrow(new RuntimeException()).when(makeService).updateModel(any());
        mvc.perform(post("/make/updateModel.htm")).andExpect(status().isOk());
    }

    @Test
    public void testSaveModel() throws Exception {
        mvc.perform(post("/make/saveModel.htm")).andExpect(status().isOk());
        doThrow(new RuntimeException()).when(makeService).addNewModel(any());
        mvc.perform(post("/make/saveModel.htm")).andExpect(status().isOk());
    }

    @Test
    public void testSearchModel() throws Exception {
        mvc.perform(post("/make/searchModel.htm")).andExpect(status().isOk());
        when(makeService.searchMakeVOs(any())).thenThrow(new RuntimeException());
        mvc.perform(post("/make/searchModel.htm")).andExpect(status().isOk());
    }

    @Test
    public void saveMakeAjax() throws Exception {
        String selectMakeName = "Apple";
        String selectMakeDesc = "Mac book";
        mvc.perform(post("/make/saveMakeAjax.htm")
                .param("selectMakeName", selectMakeName)
                .param("selectMakeDesc", selectMakeDesc))
                .andExpect(status().isOk());
        doThrow(new RuntimeException()).when(makeService).addNewMake(any());
        mvc.perform(post("/make/saveMakeAjax.htm")
                .param("selectMakeName", selectMakeName)
                .param("selectMakeDesc", selectMakeDesc))
                .andExpect(status().isOk());
    }

    @Test
    public void saveModelAjax() throws Exception {
        String selectMakeId = "1234";
        String selectModelName = "Mac book";
        mvc.perform(post("/make/saveModelAjax.htm")
                .param("selectMakeId", selectMakeId)
                .param("selectModelName", selectModelName))
                .andExpect(status().isOk());
        doThrow(new RuntimeException()).when(makeService).addNewModel(any());
        mvc.perform(post("/make/saveModelAjax.htm")
                .param("selectMakeId", selectMakeId)
                .param("selectModelName", selectModelName))
                .andExpect(status().isOk());
    }
}