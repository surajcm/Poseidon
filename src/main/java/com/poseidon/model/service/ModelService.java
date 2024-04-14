package com.poseidon.model.service;

import com.poseidon.make.dao.mapper.MakeAndModelEntityConverter;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.model.dao.ModelDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@SuppressWarnings("unused")
public class ModelService {
    private final ModelDao modelDao;
    private final MakeAndModelEntityConverter makeAndModelEntityConverter;

    public ModelService(final ModelDao modelDao, final MakeAndModelEntityConverter makeAndModelEntityConverter) {
        this.modelDao = modelDao;
        this.makeAndModelEntityConverter = makeAndModelEntityConverter;
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

    /**
     * list all makes and models.
     *
     * @return list of makes and models
     */
    public List<MakeAndModelVO> listAllMakesAndModels() {
        var models = modelDao.listAllModels();
        return makeAndModelEntityConverter.convertModelsToMakeAndModelVOs(models);
    }

    /**
     * delete a model.
     *
     * @param modelId modelId
     */
    public void deleteModel(final Long modelId) {
        modelDao.deleteModel(modelId);
    }
}
