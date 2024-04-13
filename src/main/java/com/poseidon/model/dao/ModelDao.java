package com.poseidon.model.dao;

import com.poseidon.make.dao.mapper.MakeAndModelEntityConverter;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.model.entities.Model;
import com.poseidon.model.repo.ModelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.rainerhahnekamp.sneakythrow.Sneaky.sneak;

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
        return modelRepository.findAll();
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

}
