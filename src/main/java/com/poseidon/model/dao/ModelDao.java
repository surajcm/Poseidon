package com.poseidon.model.dao;

import com.poseidon.make.dao.mapper.MakeAndModelEntityConverter;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.model.entities.Model;
import com.poseidon.model.repo.ModelRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.rainerhahnekamp.sneakythrow.Sneaky.sneak;
import static com.rainerhahnekamp.sneakythrow.Sneaky.sneaked;

@Service
public class ModelDao {
    private final ModelRepository modelRepository;
    private final MakeAndModelEntityConverter makeAndModelEntityConverter;

    public ModelDao(final ModelRepository modelRepository,
                    final MakeAndModelEntityConverter makeAndModelEntityConverter) {
        this.modelRepository = modelRepository;
        this.makeAndModelEntityConverter = makeAndModelEntityConverter;
    }

    public List<Model> listAllModels() {
        var iterable = modelRepository.findAll();
        List<Model> models = new ArrayList<>();
        iterable.forEach(models::add);
        return models;
    }

    /**
     * get model from id.
     *
     * @param modelId modelId
     * @return make and model vo
     */
    public Optional<MakeAndModelVO> getModelFromId(final Long modelId) {
        var optionalModel = sneak(() -> modelRepository.findById(modelId));
        Optional<MakeAndModelVO> makeAndModelVO = Optional.empty();
        if (optionalModel.isPresent()) {
            makeAndModelVO = Optional.of(makeAndModelEntityConverter.convertModelToMakeAndModelVO(optionalModel.get()));
        }
        return makeAndModelVO;
    }

    /**
     * delete a model.
     *
     * @param modelId id of model to be deleted
     */
    public void deleteModel(final Long modelId) {
        var consumer = sneaked(modelRepository::deleteById);
        consumer.accept(modelId);
    }

}
