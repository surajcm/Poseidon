package com.poseidon.make.service;

import com.poseidon.make.dao.MakeDao;
import com.poseidon.make.dao.entities.Make;
import com.poseidon.make.dao.mapper.MakeAndModelEntityConverter;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.domain.MakeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
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
    private static final Logger logger = LoggerFactory.getLogger(MakeService.class);

    private final MakeDao makeDAO;
    private final MakeAndModelEntityConverter makeAndModelEntityConverter;

    public MakeService(final MakeDao makeDAO, final MakeAndModelEntityConverter makeAndModelEntityConverter) {
        this.makeDAO = makeDAO;
        this.makeAndModelEntityConverter = makeAndModelEntityConverter;
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
     */
    public void addNewMake(final Make make) {
        makeDAO.addNewMake(make);
    }

    /**
     * get a make from its id.
     *
     * @param makeId makeId
     * @return make and model vo
     */
    public Optional<Make> getMakeFromId(final Long makeId) {
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
     * update make.
     *
     * @param make Make
     */
    public void updateMake(final Make make) {
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

    public List<Make> searchMakes(final String makeName) {
        return makeDAO.searchMakes(makeName);
    }

    /**
     * fetch all makes.
     *
     * @return list of makes
     */
    public List<Make> fetchAllMakes() {
        return makeDAO.fetchMakes();
    }

    public Page<Make> listAll(final int pageNumber) {
        return makeDAO.listAll(pageNumber);
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


