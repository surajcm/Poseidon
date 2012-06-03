package com.poseidon.Reports.service.impl;

import com.poseidon.Reports.service.ReportsService;
import com.poseidon.Reports.domain.ReportsVO;
import com.poseidon.Reports.dao.ReportsDAO;
import com.poseidon.Reports.exception.ReportsException;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * User: Suraj
 * Date: Jun 3, 2012
 * Time: 10:40:26 AM
 */
public class ReportsServiceImpl implements ReportsService{
    private ReportsDAO reportsDAO;
    private final Log log = LogFactory.getLog(ReportsServiceImpl.class);

    public ReportsDAO getReportsDAO() {
        return reportsDAO;
    }

    public void setReportsDAO(ReportsDAO reportsDAO) {
        this.reportsDAO = reportsDAO;
    }

    public List<ReportsVO> generateDailyReport() {
        List<ReportsVO> reportsVOs = null;
        try {
            reportsVOs = getReportsDAO().generateDailyReport();
        }catch (ReportsException e){
            e.printStackTrace();
        }
        return reportsVOs;
    }
}
