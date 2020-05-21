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
    public void testModelList() throws Exception {
        when(makeService.listAllMakesAndModels()).thenReturn(mockMakeAndModelVOs());
        mvc.perform(post("/make/ModelList.htm")).andExpect(status().is(200));
        when(makeService.listAllMakesAndModels()).thenThrow(new NullPointerException());
        when(makeService.fetchMakes()).thenThrow(new NullPointerException());
        mvc.perform(post("/make/ModelList.htm")).andExpect(status().is(200));
    }
    
    private List<MakeAndModelVO> mockMakeAndModelVOs() {
        List<MakeAndModelVO> makeAndModelVOs = new ArrayList<>();
        makeAndModelVOs.add(new MakeAndModelVO());
        makeAndModelVOs.add(new MakeAndModelVO());
        return makeAndModelVOs;
    }

    @Test
    public void testMakeList() throws Exception {
        mvc.perform(post("/make/MakeList.htm")).andExpect(status().is(200));
    }

    @Test
    public void testEditMake() throws Exception {
        mvc.perform(post("/make/editMake.htm"))
                .andExpect(status().is(200));
    }

    @Test
    public void testDeleteMake() throws Exception {
        mvc.perform(post("/make/deleteMake.htm"))
                .andExpect(status().is(200));

    }

    @Test
    public void testEditModel() throws Exception {
        mvc.perform(post("/make/editModel.htm"))
                .andExpect(status().is(200));
    }

    @Test
    public void testDeleteModel() throws Exception {
        mvc.perform(post("/make/deleteModel.htm"))
                .andExpect(status().is(200));
    }

    @Test
    public void testUpdateMake() throws Exception {
        mvc.perform(post("/make/updateMake.htm"))
                .andExpect(status().is(200));
    }

    @Test
    public void testUpdateModel() throws Exception {
        mvc.perform(post("/make/updateModel.htm"))
                .andExpect(status().is(200));
    }

    @Test
    public void testSaveModel() throws Exception {
        mvc.perform(post("/make/saveModel.htm"))
                .andExpect(status().is(200));
    }

    @Test
    public void testSearchModel() throws Exception {
        mvc.perform(post("/make/searchModel.htm"))
                .andExpect(status().is(200));
    }
}