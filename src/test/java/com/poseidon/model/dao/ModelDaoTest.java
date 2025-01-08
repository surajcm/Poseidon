package com.poseidon.model.dao;

import com.poseidon.make.dao.entities.Make;
import com.poseidon.make.dao.mapper.MakeAndModelEntityConverter;
import com.poseidon.model.entities.Model;
import com.poseidon.model.repo.ModelRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ModelDaoTest {

    private final ModelRepository modelRepository = Mockito.mock(ModelRepository.class);
    private final MakeAndModelEntityConverter converter = new MakeAndModelEntityConverter();
    private final ModelDao modelDao = new ModelDao(modelRepository, converter);

    @Test
    void modelFromIdSuccess() {
        when(modelRepository.findById(anyLong())).thenReturn(Optional.of(mockModel()));
        Assertions.assertNotNull(modelDao.getModelFromId(1234L), "Model should not be null");
    }

    @Test
    void modelFromIdSuccessOnEmpty() {
        when(modelRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertEquals(Optional.empty(), modelDao.getModelFromId(1234L),
                "Model should be empty");
    }

    @Test
    void listAllMakesAndModelsSuccess() {
        when(modelRepository.findAll()).thenReturn(mockModels());
        Assertions.assertNotNull(modelDao.listAllModels(), "Model list should not be null");
    }

    @Test
    void deleteModelSuccess() {
        Assertions.assertAll(() -> modelDao.deleteModel(1234L));
    }

    private List<Model> mockModels() {
        return List.of(mockModel());
    }

    private Model mockModel() {
        var model = new Model();
        model.setModelId(1234L);
        model.setModelName("Macbook Pro");
        model.setMake(mockMake());
        return model;
    }

    private Make mockMake() {
        var make = new Make();
        make.setId(1234L);
        make.setMakeName("HP");
        return make;
    }
}