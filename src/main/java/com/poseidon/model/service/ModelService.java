package com.poseidon.model.service;

import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.model.dao.ModelDao;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@SuppressWarnings("unused")
public class ModelService {
    private final ModelDao modelDao;

    public ModelService(final ModelDao modelDao) {
        this.modelDao = modelDao;
    }

    /**
     * get model from id.
     *
     * @param modelId modelId
     * @return make and model vo
     */
    public Optional<MakeAndModelVO> getModelFromId(final Long modelId) {
        return modelDao.getModelFromId(modelId);
    }
}
