package com.poseidon.make.service.impl;

import com.poseidon.make.dao.MakeDAO;
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
 * user: Suraj
 * Date: Jun 2, 2012
 * Time: 7:27:42 PM
 */
@Service
@SuppressWarnings("unused")
public class MakeServiceImpl implements MakeService {
    @Autowired
    private MakeDAO makeDAO;

    private static final Logger LOG = LoggerFactory.getLogger(MakeServiceImpl.class);
    
    public List<MakeAndModelVO> listAllMakesAndModels() {
        List<MakeAndModelVO> makeVOs = null;
        try {
            makeVOs = makeDAO.listAllMakesAndModels();
        } catch (MakeException makeException) {
            LOG.info("make Exception occurred " + makeException.getMessage());
        }
        return makeVOs;
    }

    public List<MakeAndModelVO> listAllMakes() {
        List<MakeAndModelVO> makeVOs = null;
        try {
            makeVOs = makeDAO.listAllMakes();
        } catch (MakeException makeException) {
            LOG.info("make Exception occurred " + makeException.getMessage());
        }
        return makeVOs;
    }

    public void addNewMake(MakeAndModelVO currentMakeVO) {
        try {
            makeDAO.addNewMake(currentMakeVO);
        } catch (MakeException makeException) {
            LOG.info("make Exception occurred " + makeException.getMessage());
        }
    }

    public MakeAndModelVO getMakeFromId(Long makeId) {
        MakeAndModelVO makeVO= null;
        try {
            makeVO = makeDAO.getMakeFromId(makeId);
        } catch (MakeException makeException) {
            LOG.info("make Exception occurred " + makeException.getMessage());
        }
        return makeVO;
    }

    public void deleteMake(Long makeId) {
        try {
            makeDAO.deleteMake(makeId);
        } catch (MakeException makeException) {
            LOG.info("make Exception occurred " + makeException.getMessage());
        }
    }

    public MakeAndModelVO getModelFromId(Long modelId) {
        MakeAndModelVO makeVO= null;
        try {
            makeVO = makeDAO.getModelFromId(modelId);
        } catch (MakeException makeException) {
            LOG.info("make Exception occurred " + makeException.getMessage());
        }
        return makeVO;
    }

    public void deleteModel(Long modelId) {
        try {
            makeDAO.deleteModel(modelId);
        } catch (MakeException makeException) {
            LOG.info("make Exception occurred " + makeException.getMessage());
        }
    }

    public void updateMake(MakeAndModelVO currentMakeVO) {
        try {
            makeDAO.updateMake(currentMakeVO);
        } catch (MakeException makeException) {
            LOG.info("make Exception occurred " + makeException.getMessage());
        }
    }

    public void addNewModel(MakeAndModelVO currentMakeVO) {
        try {
            makeDAO.addNewModel(currentMakeVO);
        } catch (MakeException makeException) {
            LOG.info("make Exception occurred " + makeException.getMessage());
        }
    }

    public void updateModel(MakeAndModelVO currentMakeVO) {
        try {
            makeDAO.updateModel(currentMakeVO);
        } catch (MakeException makeException) {
            LOG.info("make Exception occurred " + makeException.getMessage());
        }
    }

    public List<MakeAndModelVO> searchMakeVOs(MakeAndModelVO searchMakeVO) {
        List<MakeAndModelVO> makeVOs = null;
        try {
            makeVOs = makeDAO.searchMakeVOs(searchMakeVO);
        } catch (MakeException makeException) {
            LOG.info("make Exception occurred " + makeException.getMessage());
        }
        return makeVOs;
    }

    public List<MakeVO> fetchMakes() {
        List<MakeVO> makeVOs = null;
        try {
            makeVOs = makeDAO.fetchMakes();
        } catch (MakeException makeException) {
            LOG.info("make Exception occurred " + makeException.getMessage());
        }
        return makeVOs;
    }

    public List<MakeAndModelVO> getAllModelsFromMakeId(Long id) {
        List<MakeAndModelVO> makeVOs = null;
        try {
            makeVOs = makeDAO.getAllModelsFromMakeId(id);
        } catch (MakeException makeException) {
            LOG.info("make Exception occurred " + makeException.getMessage());
        }
        return makeVOs;
    }
}
