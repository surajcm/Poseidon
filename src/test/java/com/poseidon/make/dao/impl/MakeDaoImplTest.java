package com.poseidon.make.dao.impl;

import com.poseidon.make.dao.entities.Make;
import com.poseidon.make.dao.entities.Model;
import com.poseidon.make.dao.mapper.MakeAndModelEntityConverter;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.exception.MakeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class MakeDaoImplTest {
    private final MakeDaoImpl makeDao = new MakeDaoImpl();
    private final MakeRepository makeRepository = Mockito.mock(MakeRepository.class);
    private final ModelRepository modelRepository = Mockito.mock(ModelRepository.class);
    private final MakeAndModelEntityConverter makeAndModelEntityConverter = new MakeAndModelEntityConverter();


    @BeforeEach
    public void setup() {
        Whitebox.setInternalState(makeDao, "makeRepository", makeRepository);
        Whitebox.setInternalState(makeDao, "modelRepository", modelRepository);
        Whitebox.setInternalState(makeDao, "makeAndModelEntityConverter", makeAndModelEntityConverter);
    }

    @Test
    public void listAllMakesSuccess() throws MakeException {
        when(makeRepository.findAll()).thenReturn(mockMakes());
        Assertions.assertNotNull(makeDao.listAllMakes());
    }

    @Test
    public void listAllMakesFailure() {
        when(makeRepository.findAll()).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(MakeException.class, makeDao::listAllMakes);
    }

    @Test
    public void listAllMakesAndModelsSuccess() throws MakeException {
        when(modelRepository.findAll()).thenReturn(mockModels());
        Assertions.assertNotNull(makeDao.listAllMakesAndModels());
    }

    @Test
    public void listAllMakesAndModelsFailure() {
        when(modelRepository.findAll()).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(MakeException.class, makeDao::listAllMakesAndModels);
    }

    @Test
    public void addNewMakeSuccess() {
        Assertions.assertAll(() -> makeDao.addNewMake(mockMakeAndModelVO()));
    }

    @Test
    public void addNewMakeFailure() {
        when(makeRepository.save(any())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(MakeException.class, () -> makeDao.addNewMake(mockMakeAndModelVO()));
    }

    @Test
    public void updateMakeSuccess() {
        when(makeRepository.findById(anyLong())).thenReturn(Optional.of(mockMake()));
        Assertions.assertAll(() -> makeDao.updateMake(mockMakeAndModelVO()));
    }

    @Test
    public void updateMakeSuccessOnEmpty() {
        when(makeRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertAll(() -> makeDao.updateMake(mockMakeAndModelVO()));
    }

    @Test
    public void updateMakeFailure() {
        when(makeRepository.findById(anyLong())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(MakeException.class, () -> makeDao.updateMake(mockMakeAndModelVO()));
    }

    @Test
    public void getMakeFromIdSuccess() throws MakeException {
        when(makeRepository.findById(anyLong())).thenReturn(Optional.of(mockMake()));
        Assertions.assertNotNull(makeDao.getMakeFromId(1234L));
    }

    @Test
    public void getMakeFromIdSuccessOnEmpty() throws MakeException {
        when(makeRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertNull(makeDao.getMakeFromId(1234L));
    }

    @Test
    public void getMakeFromIdFailure() {
        when(makeRepository.findById(anyLong())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(MakeException.class, () -> makeDao.getMakeFromId(1234L));
    }

    @Test
    public void deleteMakeSuccess() {
        Assertions.assertAll(() -> makeDao.deleteMake(1234L));
    }

    @Test
    public void deleteMakeFailure() {
        //when()
        Assertions.assertAll(() -> makeDao.deleteMake(1234L));
    }

    private List<Make> mockMakes() {
        List<Make> makes = new ArrayList<>();
        makes.add(mockMake());
        return makes;
    }

    private List<Model> mockModels() {
        List<Model> models = new ArrayList<>();
        models.add(mockModel());
        return models;
    }

    private Make mockMake() {
        Make make = new Make();
        make.setMakeId(1234L);
        make.setMakeName("HP");
        return make;
    }

    private Model mockModel() {
        Model model = new Model();
        model.setModelId(1234L);
        model.setModelName("Macbook Pro");
        model.setMake(mockMake());
        return model;
    }

    private MakeAndModelVO mockMakeAndModelVO() {
        MakeAndModelVO makeAndModelVO = new MakeAndModelVO();
        makeAndModelVO.setMakeId(1234L);
        makeAndModelVO.setMakeName("Apple");
        makeAndModelVO.setDescription("Apple computers");
        makeAndModelVO.setCreatedBy("admin");
        makeAndModelVO.setModifiedBy("admin");
        return makeAndModelVO;
    }
}