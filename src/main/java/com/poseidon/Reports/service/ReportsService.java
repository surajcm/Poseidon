package com.poseidon.Reports.service;

import com.poseidon.Reports.domain.ReportsVO;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import java.util.List;

/**
 * User: Suraj
 * Date: Jun 3, 2012
 * Time: 10:39:48 AM
 */
public interface ReportsService {
    public List<ReportsVO> generateDailyReport();

    public JasperPrint getMakeDetailsChart(JasperReport jasperReport, ReportsVO currentReport);
}
