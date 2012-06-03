package com.poseidon.Reports.dao;

import com.poseidon.Reports.domain.ReportsVO;
import com.poseidon.Reports.exception.ReportsException;

import java.util.List;

/**
 * User: Suraj
 * Date: Jun 3, 2012
 * Time: 10:38:44 AM
 */
public interface ReportsDAO {
    public List<ReportsVO> generateDailyReport() throws ReportsException;
}
