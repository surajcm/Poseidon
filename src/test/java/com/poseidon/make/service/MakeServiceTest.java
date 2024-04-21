package com.poseidon.make.service;

import com.poseidon.make.dao.MakeDao;
import com.poseidon.make.dao.entities.Make;
import com.poseidon.make.dao.mapper.MakeAndModelEntityConverter;
import com.poseidon.make.domain.MakeAndModelVO;
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
    private final MakeAndModelEntityConverter makeAndModelEntityConverter = new MakeAndModelEntityConverter();
    private final MakeService makeService = new MakeService(makeDAO, makeAndModelEntityConverter);

    @Test
    void listAllMakesSuccess() {
        when(makeDAO.listAllMakes()).thenReturn(mockMakes());
        var makeVOs = makeService.listAllMakes();
        Assertions.assertEquals(1234L, makeVOs.get(0).getId());
    }

    @Test
    void addNewMakeSuccess() {
        Assertions.assertAll(() -> makeService.addNewMake(Mockito.mock(Make.class)));
    }

    @Test
    void makeFromIdSuccess() {
        when(makeDAO.getMakeFromId(anyLong())).thenReturn(Optional.of(Mockito.mock(Make.class)));
        Assertions.assertNotNull(makeService.getMakeFromId(1234L));
    }

    @Test
    void deleteMakeSuccess() {
        Assertions.assertAll(() -> makeService.deleteMake(1234L));
    }

    @Test
    void updateMakeSuccess() {
        Assertions.assertAll(() -> makeService.updateMake(new Make()));
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
    void updateModelWithId() {
        Assertions.assertAll(() -> makeService.updateModel(2L, 2L, "Apple"));
    }

    @Test
    void searchMakeVOsSuccess() {
        when(makeDAO.searchModels(any(MakeAndModelVO.class))).thenReturn(mockListOfMakeAndModelVO());
        var makeVOs = makeService.searchModels(Mockito.mock(MakeAndModelVO.class));
        Assertions.assertEquals(1234L, makeVOs.get(0).getId());
    }

    @Test
    void fetchAllMakesSuccess() {
        when(makeDAO.fetchMakes()).thenReturn(mockMakes());
        var makeVOs = makeService.fetchAllMakes();
        Assertions.assertEquals(1234L, makeVOs.get(0).getId());
    }

    @Test
    void allModelsFromMakeIdSuccess() {
        when(makeDAO.getAllModelsFromMakeId(anyLong())).thenReturn(mockListOfMakeAndModelVO());
        var makeVOs = makeService.getAllModelsFromMakeId(1234L);
        Assertions.assertEquals(1234L, makeVOs.get(0).getId());
    }

    @Test
    void searchMake() {
        when(makeDAO.searchMake(any())).thenReturn(mockListOfMakeAndModelVO());
        var makeVos = makeService.searchMake(new MakeAndModelVO());
        Assertions.assertEquals(makeVos.get(0).getId(), 1234L);
    }

    private List<MakeAndModelVO> mockListOfMakeAndModelVO() {
        var makeAndModelVO = new MakeAndModelVO();
        makeAndModelVO.setId(1234L);
        return List.of(makeAndModelVO);
    }

    private List<Make> mockMakes() {
        var make = new Make();
        make.setId(1234L);
        return List.of(make);
    }
}