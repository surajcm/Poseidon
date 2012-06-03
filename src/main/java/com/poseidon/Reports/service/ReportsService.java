package com.poseidon.Reports.service;

import com.poseidon.Reports.domain.ReportsVO;

import java.util.List;

/**
 * User: Suraj
 * Date: Jun 3, 2012
 * Time: 10:39:48 AM
 */
public interface ReportsService {
    public List<ReportsVO> generateDailyReport();
}
