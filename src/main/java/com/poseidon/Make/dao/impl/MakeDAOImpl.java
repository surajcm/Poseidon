package com.poseidon.Make.dao.impl;

import com.poseidon.Make.dao.MakeDAO;
import com.poseidon.Make.domain.MakeVO;
import com.poseidon.Make.exception.MakeException;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 7:28:10 PM
 */
public class MakeDAOImpl  extends JdbcDaoSupport implements MakeDAO {

    public List<MakeVO> listAllMakesAndModels() throws MakeException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
