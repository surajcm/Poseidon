package com.poseidon.Reports.dao.impl;

import com.poseidon.Reports.dao.ReportsDAO;
import com.poseidon.Reports.domain.ReportsVO;
import com.poseidon.Reports.exception.ReportsException;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * User: Suraj
 * Date: Jun 3, 2012
 * Time: 10:40:06 AM
 */
public class ReportsDAOImpl extends JdbcDaoSupport implements ReportsDAO{

    public List<ReportsVO> generateDailyReport() throws ReportsException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
