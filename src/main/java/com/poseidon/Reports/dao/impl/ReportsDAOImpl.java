package com.poseidon.Reports.dao.impl;

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

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * User: Suraj
 * Date: Jun 3, 2012
 * Time: 10:40:06 AM
 */
public class ReportsDAOImpl extends JdbcDaoSupport implements ReportsDAO{
    private final String GET_MAKE_SQL = "SELECT Id,MakeName,Description FROM make ;";

    public List<ReportsVO> generateDailyReport() throws ReportsException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public JasperPrint getMakeDetailsChart(JasperReport jasperReport, ReportsVO currentReport) throws SQLException, JRException, ReportsException {
        JasperPrint jasperPrint = new JasperPrint();
        Map<String, Object> params = new HashMap<String, Object>();

        currentReport.setMakeVOList(getMakeVOList());
        Connection connection = getJdbcTemplate().getDataSource().getConnection();
        jasperPrint = JasperFillManager.fillReport(jasperReport, params, connection);
        return jasperPrint;
    }

    private List<MakeVO> getMakeVOList() throws ReportsException {
        List<MakeVO> makeVOs = null;
        try {
            makeVOs = fetchAllMakes();
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new ReportsException(ReportsException.DATABASE_ERROR);
        }
        return makeVOs;
    }

    private List<MakeVO> fetchAllMakes() {
        return (List<MakeVO>) getJdbcTemplate().query(GET_MAKE_SQL, new MakeOnlyRowMapper());
    }

    private class MakeOnlyRowMapper implements RowMapper {
        /**
         * method to map the result to vo
         *
         * @param resultSet resultSet instance
         * @param i         i instance
         * @return UserVO as Object
         * @throws java.sql.SQLException on error
         */
        public Object mapRow(ResultSet resultSet, int i) throws SQLException {
            MakeVO makeVO = new MakeVO();
            makeVO.setId(resultSet.getLong("Id"));
            makeVO.setMakeName(resultSet.getString("MakeName"));
            makeVO.setDescription(resultSet.getString("Description"));
            return makeVO;
        }
    }

}
