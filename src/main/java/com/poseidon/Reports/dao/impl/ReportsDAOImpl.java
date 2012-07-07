package com.poseidon.Reports.dao.impl;

import com.poseidon.CompanyTerms.domain.CompanyTermsVO;
import com.poseidon.Make.domain.MakeVO;
import com.poseidon.Reports.dao.ReportsDAO;
import com.poseidon.Reports.domain.ReportsVO;
import com.poseidon.Reports.exception.ReportsException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.poseidon.Transaction.domain.TransactionReportVO;
import net.sf.jasperreports.engine.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * User: Suraj
 * Date: Jun 3, 2012
 * Time: 10:40:06 AM
 */
public class ReportsDAOImpl extends JdbcDaoSupport implements ReportsDAO {
    private final String GET_MAKE_SQL = "SELECT Id,MakeName,Description FROM make ;";

    public List<ReportsVO> generateDailyReport() throws ReportsException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public JasperPrint getMakeDetailsChart(JasperReport jasperReport, ReportsVO currentReport) throws SQLException, JRException, ReportsException {
        JasperPrint jasperPrint;
        Map<String, Object> params = new HashMap<String, Object>();
        Connection connection = getDataSource().getConnection();
        jasperPrint = JasperFillManager.fillReport(jasperReport, params, connection);
        return jasperPrint;
    }

    public JasperPrint getCallReport(JasperReport jasperReport,
                                     ReportsVO currentReport,
                                     CompanyTermsVO companyTermsVO,
                                     TransactionReportVO transactionVO) {
        JasperPrint jasperPrint = null;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("TAG", currentReport.getTagNo());
        if (companyTermsVO != null) {
            if (companyTermsVO.getTermsAndConditions() != null) {
                params.put("TERMS", companyTermsVO.getTermsAndConditions());
            }
        }
        if (transactionVO != null) {
            if (transactionVO.getDateReported() != null) {
                params.put("DATE_REP", transactionVO.getDateReported());
            } else {
                params.put("DATE_REP", "");
            }
            if (transactionVO.getCustomerName() != null) {
                params.put("CUST_NAME", transactionVO.getCustomerName());
            } else {
                params.put("CUST_NAME", "");
            }
            if (transactionVO.getAddress1() != null) {
                params.put("ADDR1", transactionVO.getAddress1());
            } else {
                params.put("ADDR1", "");
            }
            if (transactionVO.getAddress2() != null) {
                params.put("ADDR2",transactionVO.getAddress2());
            } else {
                params.put("ADDR2", "");
            }
            if (transactionVO.getPhone() != null) {
                params.put("PHONE", transactionVO.getPhone());
            } else {
                params.put("PHONE", "");
            }
            if (transactionVO.getMakeName() != null) {
                params.put("MAKE", transactionVO.getMakeName());
            } else {
                params.put("MAKE", "");
            }
            if (transactionVO.getModelName() != null) {
                params.put("MODEL", transactionVO.getModelName());
            } else {
                params.put("MODEL", "");
            }
            if (transactionVO.getSerialNo() != null) {
                params.put("SERIAL", transactionVO.getSerialNo());
            } else {
                params.put("SERIAL", "");
            }
            if (transactionVO.getAccessories() != null) {
                params.put("ACC", transactionVO.getAccessories());
            } else {
                params.put("ACC", "");
            }
            if (transactionVO.getComplaintReported() != null) {
                params.put("COMP_REP", transactionVO.getComplaintReported());
            } else {
                params.put("COMP_REP", "");
            }
            if (transactionVO.getComplaintDiagonsed() != null) {
                params.put("COMP_DIA", transactionVO.getComplaintDiagonsed());
            } else {
                params.put("COMP_DIA", "");
            }
            if (transactionVO.getEnggRemark() != null) {
                params.put("ENGG_REM", transactionVO.getEnggRemark());
            } else {
                params.put("ENGG_REM", "");
            }
            if (transactionVO.getStatus() != null) {
                params.put("STATUS", transactionVO.getStatus());
            } else {
                params.put("STATUS", "");
            }
        }
        Connection connection = null;
        try {
            connection = getDataSource().getConnection();
            jasperPrint = JasperFillManager.fillReport(jasperReport, params, connection);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return jasperPrint;
    }

}
