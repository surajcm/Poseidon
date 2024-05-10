package com.poseidon.model.service;

import com.poseidon.make.dao.mapper.MakeAndModelEntityConverter;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.model.dao.ModelDao;
import com.poseidon.model.entities.Model;
import org.springframework.data.domain.Page;
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
     * list all makes and models.
     *
     * @return list of makes and models
     */
    public List<MakeAndModelVO> listAllMakesAndModels(final int pageNumber) {
        var models = modelDao.listModels(pageNumber).stream().toList();
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

    public Page<Model> listModels(final int pageNumber) {
        return modelDao.listModels(pageNumber);
    }

    public Page<Model> searchModels(final Long makeId, final String name, final int pageNumber) {
        return modelDao.searchModels(makeId, name, pageNumber);
    }
}
