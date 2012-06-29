package com.poseidon.Reports.delegate;

import com.poseidon.Make.delegate.MakeDelegate;
import com.poseidon.Reports.domain.ReportsVO;
import com.poseidon.Reports.service.ReportsService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.jdbc.datasource.DataSourceUtils;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public JasperPrint getMakeDetailsChart(JasperReport jasperReport, ReportsVO currentReport) throws JRException {
        return getReportsService().getMakeDetailsChart(jasperReport,currentReport);

    }
}
