package com.poseidon.Make.service.impl;

import com.poseidon.Make.service.MakeService;
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

    public List<MakeVO> listAllMakesAndModels() {
        List<MakeVO> makeVOs = null;
        try {
            makeVOs = getMakeDAO().listAllMakesAndModels();
        } catch (MakeException makeException) {
            log.info("Make Exception occurred" + makeException.getMessage());
        }
        return makeVOs;
    }
}
