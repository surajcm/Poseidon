package com.poseidon.make.service;

import com.poseidon.make.dao.MakeDao;
import com.poseidon.make.dao.entities.Make;
import com.poseidon.make.dao.mapper.MakeAndModelEntityConverter;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.domain.MakeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    private final MakeAndModelEntityConverter makeAndModelEntityConverter;

    public MakeService(final MakeDao makeDAO, final MakeAndModelEntityConverter makeAndModelEntityConverter) {
        this.makeDAO = makeDAO;
        this.makeAndModelEntityConverter = makeAndModelEntityConverter;
    }

    /**
     * list all makes and models.
     *
     * @return list of makes and models
     */
    public List<MakeAndModelVO> listAllMakesAndModels() {
        var models = makeDAO.listAllModels();
        return makeAndModelEntityConverter.convertModelsToMakeAndModelVOs(models);
    }

    /**
     * list all makes.
     *
     * @return list of makes and models
     */
    public List<MakeAndModelVO> listAllMakes() {
        var makes = makeDAO.listAllMakes();
        return makeAndModelEntityConverter.convertMakeToMakeAndModelVOs(makes);
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
    public Optional<MakeAndModelVO> getMakeFromId(final Long makeId) {
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
    public Optional<MakeAndModelVO> getModelFromId(final Long modelId) {
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
        var make = makeAndModelEntityConverter.convertToMake(currentMakeVO);
        makeDAO.updateMake(make);
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

    public List<MakeVO> searchMakes(final String makeName) {
        return makeDAO.searchMakes(makeName);
    }

    /**
     * fetch all makes.
     *
     * @return list of makes
     */
    public List<Make> fetchMakes() {
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

    /**
     * Search only makes.
     * @param searchMakeAndModelVO MakeAndModelVO
     * @return List of MakeAndModelVO
     */
    public List<MakeAndModelVO> searchMake(final MakeAndModelVO searchMakeAndModelVO) {
        return makeDAO.searchMake(searchMakeAndModelVO);
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
}
