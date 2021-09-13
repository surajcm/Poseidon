package com.poseidon.make.service;

import com.poseidon.make.dao.MakeDao;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.domain.MakeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * user: Suraj.
 * Date: Jun 2, 2012
 * Time: 7:27:42 PM
 */
@Service
@SuppressWarnings("unused")
public class MakeService {
    private static final String MAKE_EXCEPTION_OCCURRED = "make Exception occurred {}";
    private static final Logger LOG = LoggerFactory.getLogger(MakeService.class);

    private final MakeDao makeDAO;

    public MakeService(final MakeDao makeDAO) {
        this.makeDAO = makeDAO;
    }

    /**
     * list all makes and models.
     *
     * @return list of makes and models
     */
    public List<MakeAndModelVO> listAllMakesAndModels() {
        return makeDAO.listAllMakesAndModels();
    }

    /**
     * list all makes.
     *
     * @return list of makes and models
     */
    public List<MakeAndModelVO> listAllMakes() {
        return makeDAO.listAllMakes();
    }

    /**
     * add a new make.
     *
     * @param currentMakeVO currentMakeVO
     */
    public void addNewMake(final MakeAndModelVO currentMakeVO) {
        makeDAO.addNewMake(currentMakeVO);
    }

    /**
     * get a make from its id.
     *
     * @param makeId makeId
     * @return make and model vo
     */
    public MakeAndModelVO getMakeFromId(final Long makeId) {
        return makeDAO.getMakeFromId(makeId);
    }

    /**
     * delete a make.
     *
     * @param makeId makeId
     */
    public void deleteMake(final Long makeId) {
        makeDAO.deleteMake(makeId);
    }

    /**
     * get model from id.
     *
     * @param modelId modelId
     * @return make and model vo
     */
    public MakeAndModelVO getModelFromId(final Long modelId) {
        return makeDAO.getModelFromId(modelId);
    }

    /**
     * delete a model.
     *
     * @param modelId modelId
     */
    public void deleteModel(final Long modelId) {
        makeDAO.deleteModel(modelId);
    }

    /**
     * update make.
     *
     * @param currentMakeVO currentMakeVO
     */
    public void updateMake(final MakeAndModelVO currentMakeVO) {
        makeDAO.updateMake(currentMakeVO);
    }

    /**
     * add a new model.
     *
     * @param currentMakeVO currentMakeVO
     */
    public void addNewModel(final MakeAndModelVO currentMakeVO) {
        makeDAO.addNewModel(currentMakeVO);
    }

    /**
     * update a model.
     *
     * @param currentMakeVO currentMakeVO
     */
    public void updateModel(final MakeAndModelVO currentMakeVO) {
        makeDAO.updateModel(currentMakeVO);
    }

    public void updateModel(final Long id, final Long makeId, final String modalModelName) {
        makeDAO.updateModel(id, makeId, modalModelName);
    }

    /**
     * search for a make.
     *
     * @param searchMakeVO searchMakeVO
     * @return list of make and models
     */
    public List<MakeAndModelVO> searchMakeVOs(final MakeAndModelVO searchMakeVO) {
        return makeDAO.searchMakeVOs(searchMakeVO);
    }

    /**
     * fetch all makes.
     *
     * @return list of makes
     */
    public List<MakeVO> fetchMakes() {
        return makeDAO.fetchMakes();
    }

    /**
     * get all models from make id.
     *
     * @param id id of make
     * @return list of make and model vo
     */
    public List<MakeAndModelVO> getAllModelsFromMakeId(final Long id) {
        return makeDAO.getAllModelsFromMakeId(id);
    }
}
