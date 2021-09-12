package com.poseidon.make.dao.repo;

import com.poseidon.make.dao.MakeDao;
import com.poseidon.make.dao.entities.Make;
import com.poseidon.make.dao.entities.Model;
import com.poseidon.make.dao.mapper.MakeAndModelEntityConverter;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.exception.MakeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class MakeDaoImplTest {
    private final MakeRepository makeRepository = Mockito.mock(MakeRepository.class);
    private final ModelRepository modelRepository = Mockito.mock(ModelRepository.class);
    private final MakeAndModelEntityConverter makeAndModelEntityConverter = new MakeAndModelEntityConverter();
    private final MakeDao makeDao = new MakeDao(makeRepository, modelRepository,
            makeAndModelEntityConverter);

    @Test
    void listAllMakesSuccess() throws MakeException {
        when(makeRepository.findAll()).thenReturn(mockMakes());
        Assertions.assertNotNull(makeDao.listAllMakes());
    }

    @Test
    void listAllMakesAndModelsSuccess() throws MakeException {
        when(modelRepository.findAll()).thenReturn(mockModels());
        Assertions.assertNotNull(makeDao.listAllMakesAndModels());
    }

    @Test
    void listAllMakesAndModelsFailure() {
        when(modelRepository.findAll()).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(MakeException.class, makeDao::listAllMakesAndModels);
    }

    @Test
    void addNewMakeSuccess() {
        Assertions.assertAll(() -> makeDao.addNewMake(mockMakeAndModelVO()));
    }

    @Test
    void addNewMakeFailure() {
        when(makeRepository.save(any())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(MakeException.class, () -> makeDao.addNewMake(mockMakeAndModelVO()));
    }

    @Test
    void updateMakeSuccess() {
        when(makeRepository.findById(anyLong())).thenReturn(Optional.of(mockMake()));
        Assertions.assertAll(() -> makeDao.updateMake(mockMakeAndModelVO()));
    }

    @Test
    void updateMakeSuccessOnEmpty() {
        when(makeRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertAll(() -> makeDao.updateMake(mockMakeAndModelVO()));
    }

    @Test
    void updateMakeFailure() {
        when(makeRepository.findById(anyLong())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(MakeException.class, () -> makeDao.updateMake(mockMakeAndModelVO()));
    }

    @Test
    void getMakeFromIdSuccess() throws MakeException {
        when(makeRepository.findById(anyLong())).thenReturn(Optional.of(mockMake()));
        Assertions.assertNotNull(makeDao.getMakeFromId(1234L));
    }

    @Test
    void getMakeFromIdSuccessOnEmpty() throws MakeException {
        when(makeRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertNull(makeDao.getMakeFromId(1234L));
    }

    @Test
    void getMakeFromIdFailure() {
        when(makeRepository.findById(anyLong())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(MakeException.class, () -> makeDao.getMakeFromId(1234L));
    }

    @Test
    void deleteMakeSuccess() {
        Assertions.assertAll(() -> makeDao.deleteMake(1234L));
    }

    @Test
    void deleteMakeFailure() {
        doThrow(new CannotAcquireLockException("DB error")).when(makeRepository).deleteById(anyLong());
        Assertions.assertThrows(MakeException.class, () -> makeDao.deleteMake(1234L));
    }

    @Test
    void getModelFromIdSuccess() throws MakeException {
        when(modelRepository.findById(anyLong())).thenReturn(Optional.of(mockModel()));
        Assertions.assertNotNull(makeDao.getModelFromId(1234L));
    }

    @Test
    void getModelFromIdSuccessOnEmpty() throws MakeException {
        when(modelRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertNull(makeDao.getModelFromId(1234L));
    }

    @Test
    void getModelFromIdFailure() {
        when(modelRepository.findById(anyLong())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(MakeException.class, () -> makeDao.getModelFromId(1234L));
    }

    @Test
    void deleteModelSuccess() {
        Assertions.assertAll(() -> makeDao.deleteModel(1234L));
    }

    @Test
    void deleteModelFailure() {
        doThrow(new CannotAcquireLockException("DB error")).when(modelRepository).deleteById(anyLong());
        Assertions.assertThrows(MakeException.class, () -> makeDao.deleteModel(1234L));
    }

    @Test
    void addNewModelSuccess() {
        when(makeRepository.findById(anyLong())).thenReturn(Optional.of(mockMake()));
        Assertions.assertAll(() -> makeDao.addNewModel(mockMakeAndModelVO()));
    }

    @Test
    void addNewModelFailure() {
        when(makeRepository.findById(anyLong())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(MakeException.class, () -> makeDao.addNewModel(mockMakeAndModelVO()));
    }

    @Test
    void updateModelSuccess() {
        when(modelRepository.findById(anyLong())).thenReturn(Optional.of(mockModel()));
        when(makeRepository.findById(anyLong())).thenReturn(Optional.of(mockMake()));
        var makeAndModelVO = getMakeAndModelVO();
        Assertions.assertAll(() -> makeDao.updateModel(makeAndModelVO));
    }

    @Test
    void updateModelSuccessOnEmpty() {
        when(modelRepository.findById(anyLong())).thenReturn(Optional.empty());
        var makeAndModelVO = getMakeAndModelVO();
        Assertions.assertAll(() -> makeDao.updateModel(makeAndModelVO));
    }

    @Test
    void updateModelFailure() {
        when(modelRepository.findById(anyLong())).thenThrow(new CannotAcquireLockException("DB error"));
        var makeAndModelVO = getMakeAndModelVO();
        Assertions.assertThrows(MakeException.class, () -> makeDao.updateModel(makeAndModelVO));
    }

    @Test
    void fetchMakesSuccess() throws MakeException {
        when(makeRepository.findAll()).thenReturn(mockMakes());
        Assertions.assertNotNull(makeDao.fetchMakes());
    }

    @Test
    void fetchMakesFailure() {
        when(makeRepository.findAll()).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(MakeException.class, makeDao::fetchMakes);
    }

    @Test
    void getAllModelsFromMakeIdSuccess() throws MakeException {
        var mockMake = mockMake();
        mockMake.setModels(mockModels());
        when(makeRepository.findById(anyLong())).thenReturn(Optional.of(mockMake));
        Assertions.assertNotNull(makeDao.getAllModelsFromMakeId(1234L));
    }

    @Test
    void getAllModelsFromMakeIdSuccessOnEmpty() throws MakeException {
        when(makeRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertNull(makeDao.getAllModelsFromMakeId(1234L));
    }

    @Test
    void getAllModelsFromMakeIdSuccessOnEmptyModels() throws MakeException {
        when(makeRepository.findById(anyLong())).thenReturn(Optional.of(mockMake()));
        Assertions.assertNull(makeDao.getAllModelsFromMakeId(1234L));
    }

    @Test
    void getAllModelsFromMakeIdFailure() {
        when(makeRepository.findById(anyLong())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(MakeException.class, () -> makeDao.getAllModelsFromMakeId(1234L));
    }

    private List<Make> mockMakes() {
        return List.of(mockMake());
    }

    private List<Model> mockModels() {
        return List.of(mockModel());
    }

    private Make mockMake() {
        var make = new Make();
        make.setMakeId(1234L);
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