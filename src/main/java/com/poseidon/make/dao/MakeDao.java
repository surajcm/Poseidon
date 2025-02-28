package com.poseidon.make.dao;

import com.poseidon.make.dao.entities.Make;
import com.poseidon.make.dao.repo.MakeRepository;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.domain.MakeVO;
import com.poseidon.model.entities.Model;
import com.poseidon.model.repo.ModelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.poseidon.init.Constants.PAGE_SIZE;
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

    public MakeDao(final MakeRepository makeRepository,
                   final ModelRepository modelRepository) {
        this.makeRepository = makeRepository;
        this.modelRepository = modelRepository;
    }

    /**
     * list all makes.
     *
     * @return list of make and model vo
     */
    public List<Make> listAllMakes() {
        return fetchMakes();
    }

    public Page<Make> listAll(final int pageNumber) {
        var pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE);
        return makeRepository.findAll(pageable);
    }

    /**
     * fetch all makes.
     *
     * @return list of make vos
     */
    public List<Make> fetchMakes() {
        var iterable = makeRepository.findAll();
        List<Make> makes = new ArrayList<>();
        iterable.forEach(makes::add);
        return makes;
    }


    /**
     * add new make.
     *
     */
    public void addNewMake(final Make make) {
        sneak(() -> makeRepository.save(make));
    }

    /**
     * update make.
     *
     * @param make Make
     */
    public void updateMake(final Make make) {
        var optionalMake =
                sneak(() -> makeRepository.findById(make.getId()));
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
    public Optional<Make> getMakeFromId(final Long makeId) {
        return makeRepository.findById(makeId);
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
     * add a new model.
     *
     * @param model Model
     */
    public void addNewModel(final Model model) {
        var model1 = updateModelWithMake(model);
        model1.setProductType("Laptop Computer");
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
    public List<MakeAndModelVO> searchModels(final MakeAndModelVO searchMakeVo) {
        List<MakeAndModelVO> makeAndModelVOS = new ArrayList<>();
        if (searchMakeVo.getMakeId() != null && searchMakeVo.getMakeId() > 0) {
            var optionalMake = sneak(() -> makeRepository.findById(searchMakeVo.getMakeId()));
            if (optionalMake.isPresent()) {
                var make = optionalMake.get();
                var models = make.getModels();
                Function<Model, MakeAndModelVO> converter = model -> getMakeAndModelVO(make, model);
                makeAndModelVOS = mapItOut(models, converter);
            }
        }
        if (hasModelName(searchMakeVo)) {
            var models = getModels(searchMakeVo, searchMakeVo.getModelName());
            Function<Model, MakeAndModelVO> converter = model -> getMakeAndModelVO(model.getMake(), model);
            makeAndModelVOS = mapItOut(models, converter);
        }
        return makeAndModelVOS;
    }

    /**
     * search make vos.
     *
     * @param searchMakeVo searchMakeVo
     * @return list of make and model vos
     */
    public  List<Make> searchMake(final MakeAndModelVO searchMakeVo) {
        var searchMakeName = searchMakeVo.getMakeName();
        return sneak(() -> makeRepository.findByMakeName(searchMakeName).parallelStream()
                        .toList());
    }

    public Page<Make> searchMakes(final String makeName, final int pageNumber) {
        var pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE);
        return makeRepository.findByMakeName(makeName, pageable);
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
        return searchMakeVO.getModelName() != null && !searchMakeVO.getModelName().isBlank();
    }
}
