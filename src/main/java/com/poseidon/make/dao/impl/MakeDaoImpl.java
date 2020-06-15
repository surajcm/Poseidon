package com.poseidon.make.dao.impl;

import com.poseidon.make.dao.MakeDao;
import com.poseidon.make.dao.entities.Make;
import com.poseidon.make.dao.entities.Model;
import com.poseidon.make.dao.mapper.MakeAndModelEntityConverter;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.domain.MakeVO;
import com.poseidon.make.exception.MakeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * user: Suraj.
 * Date: Jun 2, 2012
 * Time: 7:28:10 PM
 */
@Service
@SuppressWarnings("unused")
public class MakeDaoImpl implements MakeDao {
    private static final Logger LOG = LoggerFactory.getLogger(MakeDaoImpl.class);

    @Autowired
    private MakeRepository makeRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private MakeAndModelEntityConverter makeAndModelEntityConverter;

    /**
     * list all makes.
     *
     * @return list of make and model vo
     * @throws MakeException on error
     */
    @Override
    public List<MakeAndModelVO> listAllMakes() throws MakeException {
        List<MakeAndModelVO> makeVOs;
        try {
            List<Make> makes = makeRepository.findAll();
            makeVOs = makeAndModelEntityConverter.convertMakeToMakeAndModelVOs(makes);
        } catch (DataAccessException ex) {
            LOG.error(ex.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeVOs;
    }

    /**
     * list all makes and models.
     *
     * @return list of make and model vos
     * @throws MakeException on error
     */
    @Override
    public List<MakeAndModelVO> listAllMakesAndModels() throws MakeException {
        List<MakeAndModelVO> makeAndModelVOS;
        try {
            List<Model> models = modelRepository.findAll();
            //todo: better MakeAndModelVO to render things in a better way
            makeAndModelVOS = makeAndModelEntityConverter.convertModelsToMakeAndModelVOs(models);
        } catch (DataAccessException ex) {
            LOG.error(ex.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeAndModelVOS;
    }

    /**
     * add new make.
     *
     * @param currentMakeVo currentMakeVo
     * @throws MakeException on error
     */
    @Override
    public void addNewMake(final MakeAndModelVO currentMakeVo) throws MakeException {
        try {
            Make make = makeAndModelEntityConverter.convertToMake(currentMakeVo);
            makeRepository.save(make);
        } catch (DataAccessException ex) {
            LOG.error(ex.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
    }

    /**
     * update make.
     *
     * @param currentMakeVo currentMakeVo
     * @throws MakeException on error
     */
    @Override
    public void updateMake(final MakeAndModelVO currentMakeVo) throws MakeException {
        try {
            Make make = makeAndModelEntityConverter.convertToMake(currentMakeVo);
            Optional<Make> optionalMake = makeRepository.findById(currentMakeVo.getMakeId());
            if (optionalMake.isPresent()) {
                Make newMake = optionalMake.get();
                newMake.setMakeName(make.getMakeName());
                newMake.setDescription(make.getDescription());
                newMake.setModifiedBy(make.getModifiedBy());
                makeRepository.save(newMake);
            }
        } catch (DataAccessException ex) {
            LOG.error(ex.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
    }

    /**
     * get make from id.
     *
     * @param makeId makeId
     * @return make and model vo
     * @throws MakeException on error
     */
    @Override
    public MakeAndModelVO getMakeFromId(final Long makeId) throws MakeException {
        MakeAndModelVO makeVO = null;
        try {
            Optional<Make> optionalMake = makeRepository.findById(makeId);
            if (optionalMake.isPresent()) {
                makeVO = makeAndModelEntityConverter.getMakeVOFromMake(optionalMake.get());
            }
        } catch (DataAccessException ex) {
            LOG.error(ex.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeVO;
    }

    /**
     * delete a make.
     *
     * @param makeId makeId
     * @throws MakeException on error
     */
    @Override
    public void deleteMake(final Long makeId) throws MakeException {
        try {
            makeRepository.deleteById(makeId);
        } catch (DataAccessException ex) {
            LOG.error(ex.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
    }

    /**
     * get model from id.
     *
     * @param modelId modelId
     * @return make and model vo
     */
    @Override
    public MakeAndModelVO getModelFromId(final Long modelId) throws MakeException {
        MakeAndModelVO makeAndModelVO = null;
        try {
            Optional<Model> optionalModel = modelRepository.findById(modelId);
            if (optionalModel.isPresent()) {
                makeAndModelVO = makeAndModelEntityConverter.convertModelToMakeAndModelVO(optionalModel.get());
            }
        } catch (DataAccessException ex) {
            LOG.error(ex.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeAndModelVO;
    }

    /**
     * delete a model.
     *
     * @param modelId id of model to be deleted
     * @throws MakeException on error
     */
    @Override
    public void deleteModel(final Long modelId) throws MakeException {
        try {
            modelRepository.deleteById(modelId);
        } catch (DataAccessException ex) {
            LOG.error(ex.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
    }

    /**
     * add a new model.
     *
     * @param currentMakeVo currentMakeVo
     * @throws MakeException on error
     */
    @Override
    public void addNewModel(final MakeAndModelVO currentMakeVo) throws MakeException {
        try {
            Model model = makeAndModelEntityConverter.convertMakeAndModelVOToModel(currentMakeVo);
            Model model1 = updateModelWithMake(model);
            modelRepository.save(model1);
        } catch (DataAccessException ex) {
            LOG.error(ex.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
    }

    private Model updateModelWithMake(final Model model) {
        Optional<Make> optionalMake = makeRepository.findById(model.getMakeId());
        optionalMake.ifPresent(model::setMake);
        return model;
    }

    /**
     * update model.
     *
     * @param currentMakeVO currentMakeVO
     * @throws MakeException on error
     */
    @Override
    public void updateModel(final MakeAndModelVO currentMakeVO) throws MakeException {
        try {
            Optional<Model> optionalModel = modelRepository.findById(currentMakeVO.getId());
            if (optionalModel.isPresent()) {
                Model model = optionalModel.get();
                model.setModelName(currentMakeVO.getModelName());
                Optional<Make> optionalMake = makeRepository.findById(currentMakeVO.getMakeId());
                optionalMake.ifPresent(model::setMake);
                modelRepository.save(model);
            }
        } catch (DataAccessException ex) {
            LOG.error(ex.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
    }

    /**
     * fetch all makes.
     *
     * @return list of make vos
     * @throws MakeException on error
     */
    @Override
    public List<MakeVO> fetchMakes() throws MakeException {
        List<MakeVO> makeVOs;
        try {
            List<Make> makes = makeRepository.findAll();
            makeVOs = convertMakeToMakeVO(makes);
        } catch (DataAccessException ex) {
            LOG.error(ex.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeVOs;
    }

    /**
     * get all models from make id.
     *
     * @param makeId make id
     * @return list of make and model vo
     * @throws MakeException on error
     */
    @Override
    public List<MakeAndModelVO> getAllModelsFromMakeId(final Long makeId) throws MakeException {
        List<MakeAndModelVO> makeVOs = null;
        try {
            Optional<Make> optionalMake = makeRepository.findById(makeId);
            if (optionalMake.isPresent()) {
                Make make = optionalMake.get();
                List<Model> models = make.getModels();
                if (models != null && !models.isEmpty()) {
                    makeVOs = models.stream().map(model -> getMakeAndModelVO(make, model))
                            .collect(Collectors.toList());
                }
            }
        } catch (DataAccessException ex) {
            LOG.error(ex.getMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeVOs;
    }

    private List<MakeVO> convertMakeToMakeVO(final List<Make> makes) {
        List<MakeVO> makeVOS = new ArrayList<>();
        for (Make make : makes) {
            MakeVO makeVO = new MakeVO();
            makeVO.setId(make.getMakeId());
            makeVO.setMakeName(make.getMakeName());
            makeVO.setDescription(make.getDescription());
            makeVO.setCreatedBy(make.getCreatedBy());
            makeVO.setModifiedBy(make.getModifiedBy());
            makeVOS.add(makeVO);
        }
        return makeVOS;
    }

    private MakeAndModelVO getMakeAndModelVO(final Make make, final Model model) {
        MakeAndModelVO makeAndModelVO = new MakeAndModelVO();
        makeAndModelVO.setModelId(model.getModelId());
        makeAndModelVO.setModelName(model.getModelName());
        makeAndModelVO.setMakeId(model.getMakeId());
        makeAndModelVO.setMakeName(make.getMakeName());
        return makeAndModelVO;
    }

    /**
     * search make vos.
     *
     * @param searchMakeVo searchMakeVo
     * @return list of make and model vos
     * @throws MakeException on error
     */
    @Override
    public List<MakeAndModelVO> searchMakeVOs(final MakeAndModelVO searchMakeVo) throws MakeException {
        List<MakeAndModelVO> makeVOs;
        try {
            makeVOs = searchModels(searchMakeVo);
        } catch (DataAccessException ex) {
            LOG.error(ex.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeVOs;
    }

    private List<MakeAndModelVO> searchModels(final MakeAndModelVO searchMakeVO) {
        List<MakeAndModelVO> makeAndModelVOS = new ArrayList<>();
        if (searchMakeVO.getMakeId() != null && searchMakeVO.getMakeId() > 0) {
            Optional<Make> optionalMake = makeRepository.findById(searchMakeVO.getMakeId());
            if (optionalMake.isPresent()) {
                Make make = optionalMake.get();
                List<Model> models = make.getModels();
                makeAndModelVOS = models.stream().map(model -> getMakeAndModelVO(make, model))
                        .collect(Collectors.toList());
            }
        }
        if (searchMakeVO.getModelName() != null && searchMakeVO.getModelName().trim().length() > 0) {
            String modelName = searchMakeVO.getModelName();
            List<Model> models;
            if (searchMakeVO.getIncludes().booleanValue()) {
                models = modelRepository.findByModelNameWildCard("%" + modelName + "%");
            } else if (searchMakeVO.getStartswith().booleanValue()) {
                models = modelRepository.findByModelNameWildCard(modelName + "%");
            } else {
                models = modelRepository.findByModelName(modelName);
            }
            makeAndModelVOS = models.stream()
                    .map(model -> getMakeAndModelVO(model.getMake(), model))
                    .collect(Collectors.toList());
        }

        return makeAndModelVOS;
    }
}
