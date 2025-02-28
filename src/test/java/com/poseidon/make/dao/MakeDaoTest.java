package com.poseidon.make.dao;

import com.poseidon.make.dao.entities.Make;
import com.poseidon.make.dao.repo.MakeRepository;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.model.entities.Model;
import com.poseidon.model.repo.ModelRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class MakeDaoTest {
    private final MakeRepository makeRepository = Mockito.mock(MakeRepository.class);
    private final ModelRepository modelRepository = Mockito.mock(ModelRepository.class);
    private final MakeDao makeDao = new MakeDao(makeRepository, modelRepository);

    @Test
    void listAllMakesSuccess() {
        when(makeRepository.findAll()).thenReturn(mockMakes());
        Assertions.assertNotNull(makeDao.listAllMakes(), "Makes should not be null");
    }

    @Test
    void addNewMakeSuccess() {
        Assertions.assertAll(() -> makeDao.addNewMake(mockMake()));
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
        Assertions.assertNotNull(makeDao.getMakeFromId(1234L), "Make with id 1234 should not be null");
    }

    @Test
    void makeFromIdSuccessOnEmpty() {
        when(makeRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertEquals(Optional.empty(), makeDao.getMakeFromId(1234L),
                "Make with id 1234 should be null");
    }

    @Test
    void deleteMakeSuccess() {
        Assertions.assertAll(() -> makeDao.deleteMake(1234L));
    }

    @Test
    void addNewModelSuccess() {
        when(makeRepository.findById(anyLong())).thenReturn(Optional.of(mockMake()));
        Assertions.assertAll(() -> makeDao.addNewModel(mockModel()));
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
        Assertions.assertNotNull(makeDao.fetchMakes(), "Makes should not be null");
    }

    @Test
    void allModelsFromMakeIdSuccess() {
        var mockMake = mockMake();
        mockMake.setModels(mockModels());
        when(makeRepository.findById(anyLong())).thenReturn(Optional.of(mockMake));
        Assertions.assertNotNull(makeDao.getAllModelsFromMakeId(1234L), "Make with id 1234 should not be null");
    }

    @Test
    void allModelsFromMakeIdSuccessOnEmpty() {
        when(makeRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertNull(makeDao.getAllModelsFromMakeId(1234L), "Make with id 1234 should be null");
    }

    @Test
    void allModelsFromMakeIdSuccessOnEmptyModels() {
        when(makeRepository.findById(anyLong())).thenReturn(Optional.of(mockMake()));
        Assertions.assertNull(makeDao.getAllModelsFromMakeId(1234L), "Make with id 1234 should not be null");
    }

    @Test
    void searchMakes() {
        when(makeRepository.findByMakeName(anyString(), any())).thenReturn(pageMakes());
        Assertions.assertNotNull(makeDao.searchMakes("Apple", 1));
    }

    private Page<Make> pageMakes() {
        return new PageImpl<>(mockMakes());
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
}