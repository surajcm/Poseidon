package com.poseidon.make.service.impl;

import com.poseidon.make.dao.MakeDao;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.domain.MakeVO;
import com.poseidon.make.exception.MakeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class MakeServiceImplTest {
    private final MakeServiceImpl makeService = new MakeServiceImpl();
    private final MakeDao makeDAO = Mockito.mock(MakeDao.class);

    @BeforeEach
    public void setup() {
        Whitebox.setInternalState(makeService, "makeDAO", makeDAO);
    }

    @Test
    void listAllMakesAndModelsSuccess() {
        try {
            when(makeDAO.listAllMakesAndModels()).thenReturn(mockListOfMakeAndModelVO());
            var makeVOs = makeService.listAllMakesAndModels();
            Assertions.assertEquals(1234L, makeVOs.get(0).getId());
        } catch (MakeException exception) {
            Assertions.fail("Exception occurred");
        }
    }

    @Test
    void listAllMakesAndModelsFailure() throws MakeException {
        when(makeDAO.listAllMakesAndModels()).thenThrow(new MakeException(MakeException.DATABASE_ERROR, "DB Error"));
        Assertions.assertNull(makeService.listAllMakesAndModels());
    }

    @Test
    void listAllMakesSuccess() {
        try {
            when(makeDAO.listAllMakes()).thenReturn(mockListOfMakeAndModelVO());
            var makeVOs = makeService.listAllMakes();
            Assertions.assertEquals(1234L, makeVOs.get(0).getId());
        } catch (MakeException exception) {
            Assertions.fail("Exception occurred");
        }
    }

    @Test
    void listAllMakesFailure() throws MakeException {
        when(makeDAO.listAllMakes()).thenThrow(new MakeException(MakeException.DATABASE_ERROR, "DB Error"));
        Assertions.assertNull(makeService.listAllMakes());
    }

    @Test
    void addNewMakeSuccess() {
        Assertions.assertAll(() -> makeService.addNewMake(new MakeAndModelVO()));
    }

    @Test
    void addNewMakeFailure() throws MakeException {
        Mockito.doThrow(MakeException.class).when(makeDAO).addNewMake(any());
        Assertions.assertAll(() -> makeService.addNewMake(new MakeAndModelVO()));
    }

    @Test
    void getMakeFromIdSuccess() throws MakeException {
        when(makeDAO.getMakeFromId(anyLong())).thenReturn(Mockito.mock(MakeAndModelVO.class));
        Assertions.assertNotNull(makeService.getMakeFromId(1234L));
    }

    @Test
    void getMakeFromIdFailure() throws MakeException {
        Mockito.doThrow(MakeException.class).when(makeDAO).getMakeFromId(anyLong());
        Assertions.assertNull(makeService.getMakeFromId(1234L));
    }

    @Test
    void deleteMakeSuccess() {
        Assertions.assertAll(() -> makeService.deleteMake(1234L));
    }

    @Test
    void deleteMakeFailure() throws MakeException {
        Mockito.doThrow(MakeException.class).when(makeDAO).deleteMake(any());
        Assertions.assertAll(() -> makeService.deleteMake(1234L));
    }

    @Test
    void getModelFromIdSuccess() throws MakeException {
        when(makeDAO.getModelFromId(anyLong())).thenReturn(Mockito.mock(MakeAndModelVO.class));
        Assertions.assertNotNull(makeService.getModelFromId(1234L));
    }

    @Test
    void getModelFromIdFailure() throws MakeException {
        Mockito.doThrow(MakeException.class).when(makeDAO).getModelFromId(anyLong());
        Assertions.assertNull(makeService.getModelFromId(1234L));
    }

    @Test
    void deleteModelSuccess() {
        Assertions.assertAll(() -> makeService.deleteModel(1234L));
    }

    @Test
    void deleteModelFailure() throws MakeException {
        Mockito.doThrow(MakeException.class).when(makeDAO).deleteModel(any());
        Assertions.assertAll(() -> makeService.deleteModel(1234L));
    }

    @Test
    void updateMakeSuccess() {
        Assertions.assertAll(() -> makeService.updateMake(new MakeAndModelVO()));
    }

    @Test
    void updateMakeFailure() throws MakeException {
        Mockito.doThrow(MakeException.class).when(makeDAO).updateMake(any());
        Assertions.assertAll(() -> makeService.updateMake(new MakeAndModelVO()));
    }

    @Test
    void addNewModelSuccess() {
        Assertions.assertAll(() -> makeService.addNewModel(new MakeAndModelVO()));
    }

    @Test
    void updateModelSuccess() {
        Assertions.assertAll(() -> makeService.updateModel(new MakeAndModelVO()));
    }

    @Test
    void updateModelFailure() throws MakeException {
        Mockito.doThrow(MakeException.class).when(makeDAO).updateModel(any());
        Assertions.assertAll(() -> makeService.updateModel(new MakeAndModelVO()));
    }

    @Test
    void searchMakeVOsSuccess() {
        try {
            when(makeDAO.searchMakeVOs(any(MakeAndModelVO.class))).thenReturn(mockListOfMakeAndModelVO());
            var makeVOs = makeService.searchMakeVOs(Mockito.mock(MakeAndModelVO.class));
            Assertions.assertEquals(1234L, makeVOs.get(0).getId());
        } catch (MakeException exception) {
            Assertions.fail("Exception occurred");
        }
    }

    @Test
    void searchMakeVOsFailure() throws MakeException {
        when(makeDAO.searchMakeVOs(any(MakeAndModelVO.class)))
                .thenThrow(new MakeException(MakeException.DATABASE_ERROR, "DB Error"));
        Assertions.assertNull(makeService.searchMakeVOs(Mockito.mock(MakeAndModelVO.class)));
    }

    @Test
    void fetchMakesSuccess() {
        try {
            when(makeDAO.fetchMakes()).thenReturn(mockMakeVOs());
            var makeVOs = makeService.fetchMakes();
            Assertions.assertEquals(1234L, makeVOs.get(0).getId());
        } catch (MakeException exception) {
            Assertions.fail("Exception occurred");
        }
    }

    @Test
    void fetchMakesFailure() throws MakeException {
        when(makeDAO.fetchMakes()).thenThrow(new MakeException(MakeException.DATABASE_ERROR, "DB Error"));
        Assertions.assertNull(makeService.fetchMakes());
    }

    @Test
    void getAllModelsFromMakeIdSuccess() {
        try {
            when(makeDAO.getAllModelsFromMakeId(anyLong())).thenReturn(mockListOfMakeAndModelVO());
            var makeVOs = makeService.getAllModelsFromMakeId(1234L);
            Assertions.assertEquals(1234L, makeVOs.get(0).getId());
        } catch (MakeException exception) {
            Assertions.fail("Exception occurred");
        }
    }

    @Test
    public void getAllModelsFromMakeIdFailure() throws MakeException {
        when(makeDAO.getAllModelsFromMakeId(anyLong())).thenThrow(
                new MakeException(MakeException.DATABASE_ERROR, "DB Error"));
        Assertions.assertNull(makeService.getAllModelsFromMakeId(1234L));
    }

    private List<MakeAndModelVO> mockListOfMakeAndModelVO() {
        var makeAndModelVO = new MakeAndModelVO();
        makeAndModelVO.setId(1234L);
        return List.of(makeAndModelVO);
    }

    private List<MakeVO> mockMakeVOs() {
        var makeVO = new MakeVO();
        makeVO.setId(1234L);
        return List.of(makeVO);
    }
}