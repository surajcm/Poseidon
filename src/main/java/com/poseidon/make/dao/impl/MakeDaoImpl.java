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
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * user: Suraj
 * Date: Jun 2, 2012
 * Time: 7:28:10 PM
 */
@Repository
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
     * list all makes and models
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
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeAndModelVOS;
    }

    /**
     * list all makes
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
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeVOs;
    }

    /**
     * add new make
     *
     * @param currentMakeVo currentMakeVo
     * @throws MakeException on error
     */
    @Override
    public void addNewMake(MakeAndModelVO currentMakeVo) throws MakeException {
        try {
            Make make = makeAndModelEntityConverter.convertToMake(currentMakeVo);
            makeRepository.save(make);
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
    }

    /**
     * update make
     *
     * @param currentMakeVo currentMakeVo
     * @throws MakeException on error
     */
    @Override
    public void updateMake(MakeAndModelVO currentMakeVo) throws MakeException {
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
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
    }

    /**
     * get make from id
     *
     * @param makeId makeId
     * @return make and model vo
     * @throws MakeException on error
     */
    @Override
    public MakeAndModelVO getMakeFromId(Long makeId) throws MakeException {
        MakeAndModelVO makeVO = null;
        try {
            Optional<Make> optionalMake = makeRepository.findById(makeId);
            if (optionalMake.isPresent()) {
                makeVO = makeAndModelEntityConverter.getMakeVOFromMake(optionalMake.get());
            }
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeVO;
    }

    /**
     * delete a make
     *
     * @param makeId makeId
     * @throws MakeException on error
     */
    @Override
    public void deleteMake(Long makeId) throws MakeException {
        try {
            makeRepository.deleteById(makeId);
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
    }

    /**
     * get model from id
     *
     * @param modelId modelId
     * @return make and model vo
     */
    @Override
    public MakeAndModelVO getModelFromId(Long modelId) {
        MakeAndModelVO makeAndModelVO = null;
        Optional<Model> optionalModel = modelRepository.findById(modelId);
        if (optionalModel.isPresent()) {
            makeAndModelVO = convertModelToMakeAndModelVO(optionalModel.get());
        }
        return makeAndModelVO;
    }

    private MakeAndModelVO convertModelToMakeAndModelVO(Model model) {
        MakeAndModelVO makeAndModelVO = new MakeAndModelVO();
        makeAndModelVO.setModelId(model.getModelId());
        makeAndModelVO.setModelName(model.getModelName());
        makeAndModelVO.setMakeId(model.getMake().getMakeId());
        makeAndModelVO.setMakeName(model.getMake().getMakeName());
        return makeAndModelVO;
    }

    /**
     * delete a model
     *
     * @param modelId id of model to be deleted
     * @throws MakeException on error
     */
    @Override
    public void deleteModel(Long modelId) throws MakeException {
        try {
            modelRepository.deleteById(modelId);
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
    }

    /**
     * add a new model
     *
     * @param currentMakeVo currentMakeVo
     * @throws MakeException on error
     */
    @Override
    public void addNewModel(MakeAndModelVO currentMakeVo) throws MakeException {
        try {
            Model model = convertMakeAndModelVOToModel(currentMakeVo);
            modelRepository.save(model);
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
    }

    private Model convertMakeAndModelVOToModel(MakeAndModelVO makeAndModelVO) {
        Model model = new Model();
        model.setModelName(makeAndModelVO.getModelName());
        model.setMakeId(makeAndModelVO.getMakeId());
        model.setCreatedBy(makeAndModelVO.getCreatedBy());
        model.setModifiedBy(makeAndModelVO.getModifiedBy());
        LOG.info("in convertMakeAndModelVOToModel -> make id is : %s", makeAndModelVO.getMakeId());
        Optional<Make> optionalMake = makeRepository.findById(makeAndModelVO.getMakeId());
        optionalMake.ifPresent(m1 -> LOG.info(m1.toString()));
        optionalMake.ifPresent(model::setMake);
        return model;
    }

    /**
     * update model
     *
     * @param currentMakeVO currentMakeVO
     * @throws MakeException on error
     */
    @Override
    public void updateModel(MakeAndModelVO currentMakeVO) throws MakeException {
        try {
            Optional<Model> optionalModel = modelRepository.findById(currentMakeVO.getId());
            if (optionalModel.isPresent()) {
                Model model = optionalModel.get();
                model.setModelName(currentMakeVO.getModelName());
                Optional<Make> optionalMake = makeRepository.findById(currentMakeVO.getMakeId());
                optionalMake.ifPresent(model::setMake);
                modelRepository.save(model);
            }
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
    }

    /**
     * search make vos
     *
     * @param searchMakeVo searchMakeVo
     * @return list of make and model vos
     * @throws MakeException on error
     */
    @Override
    public List<MakeAndModelVO> searchMakeVOs(MakeAndModelVO searchMakeVo) throws MakeException {
        List<MakeAndModelVO> makeVOs;
        try {
            makeVOs = searchModels(searchMakeVo);
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeVOs;
    }

    /**
     * fetch all makes
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
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeVOs;
    }

    /**
     * get all models from make id
     *
     * @param makeId make id
     * @return list of make and model vo
     * @throws MakeException on error
     */
    @Override
    public List<MakeAndModelVO> getAllModelsFromMakeId(Long makeId) throws MakeException {
        List<MakeAndModelVO> makeVOs = new ArrayList<>();
        try {
            Optional<Make> optionalMake = makeRepository.findById(makeId);
            if (optionalMake.isPresent()) {
                Make make = optionalMake.get();
                List<Model> models = make.getModels();
                if (!models.isEmpty()) {
                    for (Model model : models) {
                        MakeAndModelVO makeAndModelVO = getMakeAndModelVO(make, model);
                        makeVOs.add(makeAndModelVO);
                    }
                }
            }
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeVOs;
    }

    private List<MakeVO> convertMakeToMakeVO(List<Make> makes) {
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

    private List<MakeAndModelVO> searchModels(MakeAndModelVO searchMakeVO) {
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
            if (searchMakeVO.getIncludes()) {
                models = modelRepository.findByModelNameWildCard("%" + modelName + "%");
            } else if (searchMakeVO.getStartswith()) {
                models = modelRepository.findByModelNameWildCard(modelName + "%");
            } else {
                models = modelRepository.findByModelName(modelName);
            }
            for (Model model : models) {
                MakeAndModelVO makeAndModelVO = getMakeAndModelVO(model.getMake(), model);
                if (!makeAndModelVOS.contains(makeAndModelVO)) {
                    makeAndModelVOS.add(makeAndModelVO);
                }
            }
        }

        return makeAndModelVOS;
    }

    private MakeAndModelVO getMakeAndModelVO(Make make, Model model) {
        MakeAndModelVO makeAndModelVO = new MakeAndModelVO();
        makeAndModelVO.setModelId(model.getModelId());
        makeAndModelVO.setModelName(model.getModelName());
        makeAndModelVO.setMakeId(model.getMakeId());
        makeAndModelVO.setMakeName(make.getMakeName());
        return makeAndModelVO;
    }
}
