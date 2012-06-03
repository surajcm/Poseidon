package com.poseidon.Reports.delegate;

import com.poseidon.Reports.domain.ReportsVO;
import com.poseidon.Reports.service.ReportsService;

import java.util.List;

/**
 * User: Suraj
 * Date: Jun 3, 2012
 * Time: 10:39:00 AM
 */
public class ReportsDelegate {
    private ReportsService reportsService;

    public ReportsService getReportsService() {
        return reportsService;
    }

    public void setReportsService(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    public List<ReportsVO> generateDailyReport() {
        return getReportsService().generateDailyReport();
    }
}
