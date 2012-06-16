package com.poseidon.Make.service.impl;

import com.poseidon.Make.service.MakeService;
import com.poseidon.Make.domain.MakeAndModelVO;
import com.poseidon.Make.domain.MakeVO;
import com.poseidon.Make.dao.MakeDAO;
import com.poseidon.Make.exception.MakeException;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 7:27:42 PM
 */
public class MakeServiceImpl implements MakeService {
    private MakeDAO makeDAO;

    private final Log log = LogFactory.getLog(MakeServiceImpl.class);

    public MakeDAO getMakeDAO() {
        return makeDAO;
    }

    public void setMakeDAO(MakeDAO makeDAO) {
        this.makeDAO = makeDAO;
    }

    public List<MakeAndModelVO> listAllMakesAndModels() {
        List<MakeAndModelVO> makeVOs = null;
        try {
            makeVOs = getMakeDAO().listAllMakesAndModels();
        } catch (MakeException makeException) {
            log.info("Make Exception occurred" + makeException.getMessage());
        }
        return makeVOs;
    }

    public List<MakeAndModelVO> listAllMakes() {
        List<MakeAndModelVO> makeVOs = null;
        try {
            makeVOs = getMakeDAO().listAllMakes();
        } catch (MakeException makeException) {
            log.info("Make Exception occurred" + makeException.getMessage());
        }
        return makeVOs;
    }

    public void addNewMake(MakeAndModelVO currentMakeVO) {
        try {
            getMakeDAO().addNewMake(currentMakeVO);
        } catch (MakeException makeException) {
            log.info("Make Exception occurred" + makeException.getMessage());
        }
    }

    public MakeAndModelVO getMakeFromId(Long makeId) {
        MakeAndModelVO makeVO= null;
        try {
            makeVO = getMakeDAO().getMakeFromId(makeId);
        } catch (MakeException makeException) {
            log.info("Make Exception occurred" + makeException.getMessage());
        }
        return makeVO;
    }

    public void deleteMake(Long makeId) {
        try {
            getMakeDAO().deleteMake(makeId);
        } catch (MakeException makeException) {
            log.info("Make Exception occurred" + makeException.getMessage());
        }
    }

    public MakeAndModelVO getModelFromId(Long modelId) {
        MakeAndModelVO makeVO= null;
        try {
            makeVO = getMakeDAO().getModelFromId(modelId);
        } catch (MakeException makeException) {
            log.info("Make Exception occurred" + makeException.getMessage());
        }
        return makeVO;
    }

    public void deleteModel(Long modelId) {
        try {
            getMakeDAO().deleteModel(modelId);
        } catch (MakeException makeException) {
            log.info("Make Exception occurred" + makeException.getMessage());
        }
    }

    public void updateMake(MakeAndModelVO currentMakeVO) {
        try {
            getMakeDAO().updateMake(currentMakeVO);
        } catch (MakeException makeException) {
            log.info("Make Exception occurred" + makeException.getMessage());
        }
    }

    public void addNewModel(MakeAndModelVO currentMakeVO) {
        try {
            getMakeDAO().addNewModel(currentMakeVO);
        } catch (MakeException makeException) {
            log.info("Make Exception occurred" + makeException.getMessage());
        }
    }

    public void updateModel(MakeAndModelVO currentMakeVO) {
        try {
            getMakeDAO().updateModel(currentMakeVO);
        } catch (MakeException makeException) {
            log.info("Make Exception occurred" + makeException.getMessage());
        }
    }

    public List<MakeAndModelVO> searchMakeVOs(MakeAndModelVO searchMakeVO) {
        List<MakeAndModelVO> makeVOs = null;
        try {
            makeVOs = getMakeDAO().searchMakeVOs(searchMakeVO);
        } catch (MakeException makeException) {
            log.info("Make Exception occurred" + makeException.getMessage());
        }
        return makeVOs;
    }

    public List<MakeVO> fetchMakes() {
        List<MakeVO> makeVOs = null;
        try {
            makeVOs = getMakeDAO().fetchMakes();
        } catch (MakeException makeException) {
            log.info("Make Exception occurred" + makeException.getMessage());
        }
        return makeVOs;
    }

    public List<MakeAndModelVO> getAllModelsFromMakeId(Long id) {
        List<MakeAndModelVO> makeVOs = null;
        try {
            makeVOs = getMakeDAO().getAllModelsFromMakeId(id);
        } catch (MakeException makeException) {
            log.info("Make Exception occurred" + makeException.getMessage());
        }
        return makeVOs;
    }
}
