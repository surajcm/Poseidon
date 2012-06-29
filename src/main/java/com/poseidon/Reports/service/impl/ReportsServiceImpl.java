package com.poseidon.Reports.service.impl;

import com.poseidon.Reports.service.ReportsService;
import com.poseidon.Reports.domain.ReportsVO;
import com.poseidon.Reports.dao.ReportsDAO;
import com.poseidon.Reports.exception.ReportsException;

import java.sql.SQLException;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
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

    public JasperPrint getMakeDetailsChart(JasperReport jasperReport, ReportsVO currentReport) {
        JasperPrint jasperPrint = new JasperPrint();
        try {
            jasperPrint = getReportsDAO().getMakeDetailsChart(jasperReport, currentReport);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JRException e) {
            e.printStackTrace();
        } catch (ReportsException e) {
            e.printStackTrace();
        }
        return jasperPrint;
    }
}
