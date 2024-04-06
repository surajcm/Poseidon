package com.poseidon.model.service;

import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.model.dao.ModelDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class ModelServiceTest {

    @Mock
    private ModelDao modelDao;

    @InjectMocks
    private ModelService modelService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
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
}