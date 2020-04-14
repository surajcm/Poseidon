package com.poseidon.make.service.impl;

import com.poseidon.make.dao.MakeDao;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.domain.MakeVO;
import com.poseidon.make.exception.MakeException;
import com.poseidon.make.service.MakeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * user: Suraj.
 * Date: Jun 2, 2012
 * Time: 7:27:42 PM
 */
@Service
@SuppressWarnings("unused")
public class MakeServiceImpl implements MakeService {
    @Autowired
    private MakeDao makeDAO;

    private static final Logger LOG = LoggerFactory.getLogger(MakeServiceImpl.class);

    /**
     * list all makes and models.
     *
     * @return list of makes and models
     */
    @Override
    public List<MakeAndModelVO> listAllMakesAndModels() {
        List<MakeAndModelVO> makeVOs = null;
        try {
            makeVOs = makeDAO.listAllMakesAndModels();
        } catch (MakeException makeException) {
            LOG.info("make Exception occurred " + makeException.getMessage());
        }
        return makeVOs;
    }

    /**
     * list all makes.
     *
     * @return list of makes and models
     */
    @Override
    public List<MakeAndModelVO> listAllMakes() {
        List<MakeAndModelVO> makeVOs = null;
        try {
            makeVOs = makeDAO.listAllMakes();
        } catch (MakeException makeException) {
            LOG.info("make Exception occurred " + makeException.getMessage());
        }
        return makeVOs;
    }

    /**
     * add a new make.
     *
     * @param currentMakeVO currentMakeVO
     */
    @Override
    public void addNewMake(final MakeAndModelVO currentMakeVO) {
        try {
            makeDAO.addNewMake(currentMakeVO);
        } catch (MakeException makeException) {
            LOG.info("make Exception occurred " + makeException.getMessage());
        }
    }

    /**
     * get a make from its id.
     *
     * @param makeId makeId
     * @return make and model vo
     */
    @Override
    public MakeAndModelVO getMakeFromId(final Long makeId) {
        MakeAndModelVO makeVO = null;
        try {
            makeVO = makeDAO.getMakeFromId(makeId);
        } catch (MakeException makeException) {
            LOG.info("make Exception occurred " + makeException.getMessage());
        }
        return makeVO;
    }

    /**
     * delete a make.
     *
     * @param makeId makeId
     */
    @Override
    public void deleteMake(final Long makeId) {
        try {
            makeDAO.deleteMake(makeId);
        } catch (MakeException makeException) {
            LOG.info("make Exception occurred " + makeException.getMessage());
        }
    }

    /**
     * get model from id.
     *
     * @param modelId modelId
     * @return make and model vo
     */
    @Override
    public MakeAndModelVO getModelFromId(final Long modelId) {
        MakeAndModelVO makeVO = null;
        try {
            makeVO = makeDAO.getModelFromId(modelId);
        } catch (MakeException makeException) {
            LOG.info("make Exception occurred " + makeException.getMessage());
        }
        return makeVO;
    }

    /**
     * delete a model.
     *
     * @param modelId modelId
     */
    @Override
    public void deleteModel(final Long modelId) {
        try {
            makeDAO.deleteModel(modelId);
        } catch (MakeException makeException) {
            LOG.info("make Exception occurred " + makeException.getMessage());
        }
    }

    /**
     * update make.
     *
     * @param currentMakeVO currentMakeVO
     */
    @Override
    public void updateMake(final MakeAndModelVO currentMakeVO) {
        try {
            makeDAO.updateMake(currentMakeVO);
        } catch (MakeException makeException) {
            LOG.info("make Exception occurred " + makeException.getMessage());
        }
    }

    /**
     * add a new model.
     *
     * @param currentMakeVO currentMakeVO
     */
    @Override
    public void addNewModel(final MakeAndModelVO currentMakeVO) {
        try {
            makeDAO.addNewModel(currentMakeVO);
        } catch (MakeException makeException) {
            LOG.info("make Exception occurred " + makeException.getMessage());
        }
    }

    /**
     * update a model.
     *
     * @param currentMakeVO currentMakeVO
     */
    @Override
    public void updateModel(final MakeAndModelVO currentMakeVO) {
        try {
            makeDAO.updateModel(currentMakeVO);
        } catch (MakeException makeException) {
            LOG.info("make Exception occurred " + makeException.getMessage());
        }
    }

    /**
     * search for a make.
     *
     * @param searchMakeVO searchMakeVO
     * @return list of make and models
     */
    @Override
    public List<MakeAndModelVO> searchMakeVOs(final MakeAndModelVO searchMakeVO) {
        List<MakeAndModelVO> makeVOs = null;
        try {
            makeVOs = makeDAO.searchMakeVOs(searchMakeVO);
        } catch (MakeException makeException) {
            LOG.info("make Exception occurred " + makeException.getMessage());
        }
        return makeVOs;
    }

    /**
     * fetch all makes.
     *
     * @return list of makes
     */
    @Override
    public List<MakeVO> fetchMakes() {
        List<MakeVO> makeVOs = null;
        try {
            makeVOs = makeDAO.fetchMakes();
        } catch (MakeException makeException) {
            LOG.info("make Exception occurred " + makeException.getMessage());
        }
        return makeVOs;
    }

    /**
     * get all models from make id.
     *
     * @param id id of make
     * @return list of make and model vo
     */
    @Override
    public List<MakeAndModelVO> getAllModelsFromMakeId(final Long id) {
        List<MakeAndModelVO> makeVOs = null;
        try {
            makeVOs = makeDAO.getAllModelsFromMakeId(id);
        } catch (MakeException makeException) {
            LOG.info("make Exception occurred " + makeException.getMessage());
        }
        return makeVOs;
    }
}
