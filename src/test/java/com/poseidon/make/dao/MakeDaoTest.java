package com.poseidon.make.dao;

import com.poseidon.make.dao.entities.Make;
import com.poseidon.model.entities.Model;
import com.poseidon.make.dao.mapper.MakeAndModelEntityConverter;
import com.poseidon.make.dao.repo.MakeRepository;
import com.poseidon.model.repo.ModelRepository;
import com.poseidon.make.domain.MakeAndModelVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class MakeDaoTest {
    private final MakeRepository makeRepository = Mockito.mock(MakeRepository.class);
    private final ModelRepository modelRepository = Mockito.mock(ModelRepository.class);
    private final MakeAndModelEntityConverter makeAndModelEntityConverter = new MakeAndModelEntityConverter();
    private final MakeDao makeDao = new MakeDao(makeRepository, modelRepository,
            makeAndModelEntityConverter);

    @Test
    void listAllMakesSuccess() {
        when(makeRepository.findAll()).thenReturn(mockMakes());
        Assertions.assertNotNull(makeDao.listAllMakes());
    }

    @Test
    void listAllMakesAndModelsSuccess() {
        when(modelRepository.findAll()).thenReturn(mockModels());
        Assertions.assertNotNull(makeDao.listAllModels());
    }

    @Test
    void addNewMakeSuccess() {
        Assertions.assertAll(() -> makeDao.addNewMake("Apple", "Apple", "Apple"));
    }

    @Test
    void updateMakeSuccess() {
        when(makeRepository.findById(anyLong())).thenReturn(Optional.of(mockMake()));
        Assertions.assertAll(() -> makeDao.updateMake(mockMake()));
    }

    @Test
    void updateMakeSuccessOnEmpty() {
        when(makeRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertAll(() -> makeDao.updateMake(mockMake()));
    }

    @Test
    void makeFromIdSuccess() {
        when(makeRepository.findById(anyLong())).thenReturn(Optional.of(mockMake()));
        Assertions.assertNotNull(makeDao.getMakeFromId(1234L));
    }

    @Test
    void makeFromIdSuccessOnEmpty() {
        when(makeRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertEquals(Optional.empty(), makeDao.getMakeFromId(1234L));
    }

    @Test
    void deleteMakeSuccess() {
        Assertions.assertAll(() -> makeDao.deleteMake(1234L));
    }

    @Test
    void deleteModelSuccess() {
        Assertions.assertAll(() -> makeDao.deleteModel(1234L));
    }

    @Test
    void addNewModelSuccess() {
        when(makeRepository.findById(anyLong())).thenReturn(Optional.of(mockMake()));
        Assertions.assertAll(() -> makeDao.addNewModel(mockMakeAndModelVO()));
    }

    @Test
    void updateModelSuccess() {
        when(modelRepository.findById(anyLong())).thenReturn(Optional.of(mockModel()));
        when(makeRepository.findById(anyLong())).thenReturn(Optional.of(mockMake()));
        var makeAndModelVO = getMakeAndModelVO();
        Assertions.assertAll(() -> makeDao.updateModel(makeAndModelVO));
    }

    @Test
    void updateModelSuccess2() {
        when(modelRepository.findById(anyLong())).thenReturn(Optional.of(mockModel()));
        when(makeRepository.findById(anyLong())).thenReturn(Optional.of(mockMake()));
        Assertions.assertAll(() -> makeDao.updateModel(1L, 1L, "Apple"));
    }

    @Test
    void updateModelSuccessOnEmpty() {
        when(modelRepository.findById(anyLong())).thenReturn(Optional.empty());
        var makeAndModelVO = getMakeAndModelVO();
        Assertions.assertAll(() -> makeDao.updateModel(makeAndModelVO));
    }

    @Test
    void fetchMakesSuccess() {
        when(makeRepository.findAll()).thenReturn(mockMakes());
        Assertions.assertNotNull(makeDao.fetchMakes());
    }

    @Test
    void allModelsFromMakeIdSuccess() {
        var mockMake = mockMake();
        mockMake.setModels(mockModels());
        when(makeRepository.findById(anyLong())).thenReturn(Optional.of(mockMake));
        Assertions.assertNotNull(makeDao.getAllModelsFromMakeId(1234L));
    }

    @Test
    void allModelsFromMakeIdSuccessOnEmpty() {
        when(makeRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertNull(makeDao.getAllModelsFromMakeId(1234L));
    }

    @Test
    void allModelsFromMakeIdSuccessOnEmptyModels() {
        when(makeRepository.findById(anyLong())).thenReturn(Optional.of(mockMake()));
        Assertions.assertNull(makeDao.getAllModelsFromMakeId(1234L));
    }

    @Test
    void searchMakes() {
        when(makeRepository.findByMakeName(anyString())).thenReturn(mockMakes());
        Assertions.assertNotNull(makeDao.searchMakes("Apple"));
    }

    private List<Make> mockMakes() {
        return List.of(mockMake());
    }

    private List<Model> mockModels() {
        return List.of(mockModel());
    }

    private Make mockMake() {
        var make = new Make();
        make.setId(1234L);
        make.setMakeName("HP");
        return make;
    }

    private Model mockModel() {
        var model = new Model();
        model.setModelId(1234L);
        model.setModelName("Macbook Pro");
        model.setMake(mockMake());
        return model;
    }

    private MakeAndModelVO getMakeAndModelVO() {
        var makeAndModelVO = new MakeAndModelVO();
        makeAndModelVO.setId(1234L);
        makeAndModelVO.setModelName("Macbook");
        makeAndModelVO.setMakeId(1234L);
        return makeAndModelVO;
    }

    private MakeAndModelVO mockMakeAndModelVO() {
        var makeAndModelVO = new MakeAndModelVO();
        makeAndModelVO.setMakeId(1234L);
        makeAndModelVO.setMakeName("Apple");
        makeAndModelVO.setDescription("Apple computers");
        makeAndModelVO.setCreatedBy("admin");
        makeAndModelVO.setModifiedBy("admin");
        return makeAndModelVO;
    }
}