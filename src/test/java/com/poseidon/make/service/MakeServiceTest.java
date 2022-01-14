package com.poseidon.make.service;

import com.poseidon.make.dao.MakeDao;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.domain.MakeVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class MakeServiceTest {
    private final MakeDao makeDAO = Mockito.mock(MakeDao.class);
    private final MakeService makeService = new MakeService(makeDAO);

    @Test
    void listAllMakesAndModelsSuccess() {
        when(makeDAO.listAllMakesAndModels()).thenReturn(mockListOfMakeAndModelVO());
        var makeVOs = makeService.listAllMakesAndModels();
        Assertions.assertEquals(1234L, makeVOs.get(0).getId());
    }

    @Test
    void listAllMakesSuccess() {
        when(makeDAO.listAllMakes()).thenReturn(mockListOfMakeAndModelVO());
        var makeVOs = makeService.listAllMakes();
        Assertions.assertEquals(1234L, makeVOs.get(0).getId());
    }

    @Test
    void addNewMakeSuccess() {
        Assertions.assertAll(() -> makeService.addNewMake(new MakeAndModelVO()));
    }

    @Test
    void getMakeFromIdSuccess() {
        when(makeDAO.getMakeFromId(anyLong())).thenReturn(Optional.of(Mockito.mock(MakeAndModelVO.class)));
        Assertions.assertNotNull(makeService.getMakeFromId(1234L));
    }

    @Test
    void deleteMakeSuccess() {
        Assertions.assertAll(() -> makeService.deleteMake(1234L));
    }

    @Test
    void getModelFromIdSuccess() {
        when(makeDAO.getModelFromId(anyLong())).thenReturn(Optional.of(Mockito.mock(MakeAndModelVO.class)));
        Assertions.assertNotNull(makeService.getModelFromId(1234L));
    }

    @Test
    void deleteModelSuccess() {
        Assertions.assertAll(() -> makeService.deleteModel(1234L));
    }

    @Test
    void updateMakeSuccess() {
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
    void searchMakeVOsSuccess() {
        when(makeDAO.searchMakeVOs(any(MakeAndModelVO.class))).thenReturn(mockListOfMakeAndModelVO());
        var makeVOs = makeService.searchMakeVOs(Mockito.mock(MakeAndModelVO.class));
        Assertions.assertEquals(1234L, makeVOs.get(0).getId());
    }

    @Test
    void fetchMakesSuccess() {
        when(makeDAO.fetchMakes()).thenReturn(mockMakeVOs());
        var makeVOs = makeService.fetchMakes();
        Assertions.assertEquals(1234L, makeVOs.get(0).getId());
    }

    @Test
    void getAllModelsFromMakeIdSuccess() {
        when(makeDAO.getAllModelsFromMakeId(anyLong())).thenReturn(mockListOfMakeAndModelVO());
        var makeVOs = makeService.getAllModelsFromMakeId(1234L);
        Assertions.assertEquals(1234L, makeVOs.get(0).getId());
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