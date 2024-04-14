package com.poseidon.model.service;

import com.poseidon.make.dao.entities.Make;
import com.poseidon.make.dao.mapper.MakeAndModelEntityConverter;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.model.dao.ModelDao;
import com.poseidon.model.entities.Model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class ModelServiceTest {

    private final ModelDao modelDao = Mockito.mock(ModelDao.class);
    private final MakeAndModelEntityConverter makeAndModelEntityConverter = new MakeAndModelEntityConverter();
    private final ModelService modelService = new ModelService(modelDao, makeAndModelEntityConverter);

    @Test
    void listAllMakesAndModelsSuccess() {
        when(modelDao.listAllModels()).thenReturn(mockModels());
        var makeVOs = modelService.listAllMakesAndModels();
        Assertions.assertEquals(1234L, makeVOs.get(0).getId());
    }

    @Test
    public void testGetModelFromId() {
        Long modelId = 1L;
        MakeAndModelVO makeAndModelVO = new MakeAndModelVO();
        when(modelDao.getModelFromId(modelId)).thenReturn(Optional.of(makeAndModelVO));

        Optional<MakeAndModelVO> result = modelService.getModelFromId(modelId);

        assertEquals(Optional.of(makeAndModelVO), result);
    }

    @Test
    void modelFromIdSuccess() {
        when(modelDao.getModelFromId(anyLong())).thenReturn(Optional.of(Mockito.mock(MakeAndModelVO.class)));
        Assertions.assertNotNull(modelService.getModelFromId(1234L));
    }


    @Test
    void deleteModelSuccess() {
        Assertions.assertAll(() -> modelService.deleteModel(1234L));
    }

    private List<Model> mockModels() {
        var model = new Model();
        model.setId(1234L);
        model.setModelId(1234L);
        model.setMake(mockMakes().get(0));
        return List.of(model);
    }

    private List<Make> mockMakes() {
        var make = new Make();
        make.setId(1234L);
        return List.of(make);
    }
}