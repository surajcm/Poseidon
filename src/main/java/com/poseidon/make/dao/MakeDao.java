package com.poseidon.make.dao;

import com.poseidon.make.dao.entities.Make;
import com.poseidon.make.dao.entities.Model;
import com.poseidon.make.dao.mapper.MakeAndModelEntityConverter;
import com.poseidon.make.dao.repo.MakeRepository;
import com.poseidon.make.dao.repo.ModelRepository;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.domain.MakeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.rainerhahnekamp.sneakythrow.Sneaky.sneak;
import static com.rainerhahnekamp.sneakythrow.Sneaky.sneaked;

/**
 * user: Suraj.
 * Date: Jun 2, 2012
 * Time: 7:28:10 PM
 */
@Service
@SuppressWarnings("unused")
public class MakeDao {
    private static final Logger LOG = LoggerFactory.getLogger(MakeDao.class);

    private final MakeRepository makeRepository;

    private final ModelRepository modelRepository;

    private final MakeAndModelEntityConverter makeAndModelEntityConverter;

    public MakeDao(final MakeRepository makeRepository,
                   final ModelRepository modelRepository,
                   final MakeAndModelEntityConverter makeAndModelEntityConverter) {
        this.makeRepository = makeRepository;
        this.modelRepository = modelRepository;
        this.makeAndModelEntityConverter = makeAndModelEntityConverter;
    }

    /**
     * list all makes.
     *
     * @return list of make and model vo
     */
    public List<MakeAndModelVO> listAllMakes() {
        var makes = sneak(makeRepository::findAll);
        return makeAndModelEntityConverter.convertMakeToMakeAndModelVOs(makes);
    }

    /**
     * list all makes and models.
     *
     * @return list of make and model vos
     */
    public List<MakeAndModelVO> listAllMakesAndModels() {
        var models = sneak(modelRepository::findAll);
        //todo: better MakeAndModelVO to render things in a better way
        return makeAndModelEntityConverter.convertModelsToMakeAndModelVOs(models);
    }

    /**
     * add new make.
     *
     * @param currentMakeVo currentMakeVo
     */
    public void addNewMake(final MakeAndModelVO currentMakeVo) {
        var make = makeAndModelEntityConverter.convertToMake(currentMakeVo);
        sneak(() -> makeRepository.save(make));
    }

    /**
     * update make.
     *
     * @param currentMakeVo currentMakeVo
     */
    public void updateMake(final MakeAndModelVO currentMakeVo) {
        var make = makeAndModelEntityConverter.convertToMake(currentMakeVo);
        var optionalMake =
                sneak(() -> makeRepository.findById(currentMakeVo.getMakeId()));
        if (optionalMake.isPresent()) {
            var newMake = optionalMake.get();
            newMake.setMakeName(make.getMakeName());
            newMake.setDescription(make.getDescription());
            newMake.setModifiedBy(make.getModifiedBy());
            sneak(() -> makeRepository.save(newMake));
        }
    }

    /**
     * get make from id.
     *
     * @param makeId makeId
     * @return make and model vo
     */
    public MakeAndModelVO getMakeFromId(final Long makeId) {
        MakeAndModelVO makeVO = null;
        var optionalMake = sneak(() -> makeRepository.findById(makeId));
        if (optionalMake.isPresent()) {
            makeVO = makeAndModelEntityConverter.getMakeVOFromMake(optionalMake.get());
        }
        return makeVO;
    }

    /**
     * delete a make.
     *
     * @param makeId makeId
     */
    public void deleteMake(final Long makeId) {
        var consumer = sneaked(makeRepository::deleteById);
        consumer.accept(makeId);
    }

    /**
     * get model from id.
     *
     * @param modelId modelId
     * @return make and model vo
     */
    public MakeAndModelVO getModelFromId(final Long modelId) {
        MakeAndModelVO makeAndModelVO = null;
        var optionalModel = sneak(() -> modelRepository.findById(modelId));
        if (optionalModel.isPresent()) {
            makeAndModelVO = makeAndModelEntityConverter.convertModelToMakeAndModelVO(optionalModel.get());
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

    /**
     * add a new model.
     *
     * @param currentMakeVo currentMakeVo
     */
    public void addNewModel(final MakeAndModelVO currentMakeVo) {
        var model = makeAndModelEntityConverter.convertMakeAndModelVOToModel(currentMakeVo);
        var model1 = updateModelWithMake(model);
        sneak(() -> modelRepository.save(model1));
    }

    private Model updateModelWithMake(final Model model) {
        var optionalMake = sneak(() -> makeRepository.findById(model.getMakeId()));
        optionalMake.ifPresent(model::setMake);
        return model;
    }

    /**
     * update model.
     *
     * @param currentMakeVO currentMakeVO
     */
    public void updateModel(final MakeAndModelVO currentMakeVO) {
        var optionalModel = sneak(() -> modelRepository.findById(currentMakeVO.getId()));
        if (optionalModel.isPresent()) {
            var model = optionalModel.get();
            model.setModelName(currentMakeVO.getModelName());
            var optionalMake = sneak(() -> makeRepository.findById(currentMakeVO.getMakeId()));
            optionalMake.ifPresent(model::setMake);
            sneak(() -> modelRepository.save(model));
        }
    }

    public void updateModel(final Long id, final Long makeId, final String modalModelName) {
        var optionalModel = sneak(() -> modelRepository.findById(id));
        if (optionalModel.isPresent()) {
            var model = optionalModel.get();
            model.setModelName(modalModelName);
            var optionalMake = sneak(() -> makeRepository.findById(makeId));
            optionalMake.ifPresent(model::setMake);
            sneak(() -> modelRepository.save(model));
        }
    }

    /**
     * fetch all makes.
     *
     * @return list of make vos
     */
    public List<MakeVO> fetchMakes() {
        var makes = sneak(makeRepository::findAll);
        return convertMakeToMakeVO(makes);
    }

    /**
     * get all models from make id.
     *
     * @param makeId make id
     * @return list of make and model vo
     */
    public List<MakeAndModelVO> getAllModelsFromMakeId(final Long makeId) {
        List<MakeAndModelVO> makeVOs = null;
        var optionalMake = sneak(() -> makeRepository.findById(makeId));
        if (optionalMake.isPresent()) {
            var make = optionalMake.get();
            var models = make.getModels();
            if (models != null && !models.isEmpty()) {
                makeVOs = models.stream().map(model -> getMakeAndModelVO(make, model))
                        .toList();
            }
        }
        return makeVOs;
    }

    private List<MakeVO> convertMakeToMakeVO(final List<Make> makes) {
        return makes.stream().map(this::createMakeVO).toList();
    }

    private MakeVO createMakeVO(final Make make) {
        var makeVO = new MakeVO();
        makeVO.setId(make.getId());
        makeVO.setMakeName(make.getMakeName());
        makeVO.setDescription(make.getDescription());
        makeVO.setCreatedBy(make.getCreatedBy());
        makeVO.setModifiedBy(make.getModifiedBy());
        return makeVO;
    }

    private MakeAndModelVO getMakeAndModelVO(final Make make, final Model model) {
        var makeAndModelVO = new MakeAndModelVO();
        makeAndModelVO.setModelId(model.getModelId());
        makeAndModelVO.setModelName(model.getModelName());
        makeAndModelVO.setMakeId(make.getId());
        makeAndModelVO.setMakeName(make.getMakeName());
        return makeAndModelVO;
    }

    /**
     * search make vos.
     *
     * @param searchMakeVo searchMakeVo
     * @return list of make and model vos
     */
    public List<MakeAndModelVO> searchMakeVOs(final MakeAndModelVO searchMakeVo) {
        return searchModels(searchMakeVo);
    }

    private List<MakeAndModelVO> searchModels(final MakeAndModelVO searchMakeVO) {
        List<MakeAndModelVO> makeAndModelVOS = new ArrayList<>();
        if (searchMakeVO.getMakeId() != null && searchMakeVO.getMakeId() > 0) {
            var optionalMake = sneak(() -> makeRepository.findById(searchMakeVO.getMakeId()));
            if (optionalMake.isPresent()) {
                var make = optionalMake.get();
                var models = make.getModels();
                Function<Model, MakeAndModelVO> converter = model -> getMakeAndModelVO(make, model);
                makeAndModelVOS = mapItOut(models, converter);
            }
        }
        if (hasModelName(searchMakeVO)) {
            var models = getModels(searchMakeVO, searchMakeVO.getModelName());
            Function<Model, MakeAndModelVO> converter = model -> getMakeAndModelVO(model.getMake(), model);
            makeAndModelVOS = mapItOut(models, converter);
        }
        return makeAndModelVOS;
    }

    private List<MakeAndModelVO> mapItOut(final List<Model> models,
                                          final Function<Model, MakeAndModelVO> converter) {
        return models.stream()
                .map(converter)
                .toList();
    }

    private List<Model> getModels(final MakeAndModelVO searchMakeVO, final String modelName) {
        List<Model> models;
        if (Boolean.TRUE.equals(searchMakeVO.getIncludes())) {
            models = modelRepository.findByModelNameWildCard("%" + modelName + "%");
        } else if (Boolean.TRUE.equals(searchMakeVO.getStartswith())) {
            models = modelRepository.findByModelNameWildCard(modelName + "%");
        } else {
            models = modelRepository.findByModelName(modelName);
        }
        return models;
    }

    private boolean hasModelName(final MakeAndModelVO searchMakeVO) {
        return searchMakeVO.getModelName() != null && searchMakeVO.getModelName().trim().length() > 0;
    }
}
