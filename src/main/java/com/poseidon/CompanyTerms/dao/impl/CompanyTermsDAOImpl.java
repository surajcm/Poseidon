package com.poseidon.CompanyTerms.dao.impl;

import com.poseidon.CompanyTerms.dao.CompanyTermsDAO;
import com.poseidon.CompanyTerms.domain.CompanyTermsVO;
import com.poseidon.CompanyTerms.exception.CompanyTermsException;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.dao.DataAccessException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 10:00:05 PM
 */
public class CompanyTermsDAOImpl extends JdbcDaoSupport implements CompanyTermsDAO {
    //logger
    private final Log log = LogFactory.getLog(CompanyTermsDAOImpl.class);

    private final String GET_COMPANY_TERMS_SQL = "SELECT Id, Terms, CompanyDetails FROM companyterms ;";
    private final String GET_COMPANY_SQL = "SELECT Id, CompanyDetails FROM companyterms ;";
    private final String GET_TERMS_SQL = "SELECT Id, Terms FROM companyterms ;";
    private final String UPDATE_TERMS_SQL = "update companyterms set Terms = ? where Id = 1 ;";
    private final String UPDATE_COMPANY_SQL = "update companyterms set CompanyDetails = ? where Id = 1 ;";

    public CompanyTermsVO listCompanyTerms() throws CompanyTermsException {
        CompanyTermsVO companyTermsVO= null;
        try {
            List<CompanyTermsVO> companyTermsVOs =fetchCompanyTerms();
            if(companyTermsVOs != null && companyTermsVOs.size() > 0){

                companyTermsVO = companyTermsVOs.get(0);
            }
        } catch (DataAccessException e) {
            throw new CompanyTermsException(CompanyTermsException.DATABASE_ERROR);
        }
        return companyTermsVO;
    }

    public CompanyTermsVO fetchCompany() throws CompanyTermsException {
        CompanyTermsVO companyTermsVO= null;
        try {
            List<CompanyTermsVO> companyTermsVOs = fetchCompanyOnly();
            if(companyTermsVOs != null && companyTermsVOs.size() > 0){

                companyTermsVO = companyTermsVOs.get(0);
            }
        } catch (DataAccessException e) {
            throw new CompanyTermsException(CompanyTermsException.DATABASE_ERROR);
        }
        return companyTermsVO;
    }

    private List<CompanyTermsVO> fetchCompanyOnly() {
        return (List<CompanyTermsVO>) getJdbcTemplate().query(GET_COMPANY_SQL, new CompanyRowMapper());
    }

    public CompanyTermsVO fetchTerms() throws CompanyTermsException {
        CompanyTermsVO companyTermsVO= null;
        try {
            List<CompanyTermsVO> companyTermsVOs = fetchTermsOnly();
            if(companyTermsVOs != null && companyTermsVOs.size() > 0){

                companyTermsVO = companyTermsVOs.get(0);
            }
        } catch (DataAccessException e) {
            throw new CompanyTermsException(CompanyTermsException.DATABASE_ERROR);
        }
        return companyTermsVO;
    }

    public void updateTerms(String termsAndConditions) throws CompanyTermsException {
        Object[] parameters = new Object[]{termsAndConditions};

        try {
            getJdbcTemplate().update(UPDATE_TERMS_SQL, parameters);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new CompanyTermsException(CompanyTermsException.DATABASE_ERROR);
        }
    }

    public void updateCompany(String companyDetails) throws CompanyTermsException {
        Object[] parameters = new Object[]{companyDetails};

        try {
            getJdbcTemplate().update(UPDATE_COMPANY_SQL, parameters);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new CompanyTermsException(CompanyTermsException.DATABASE_ERROR);
        }
    }

    private List<CompanyTermsVO> fetchTermsOnly() {
        return (List<CompanyTermsVO>) getJdbcTemplate().query(GET_TERMS_SQL, new TermsRowMapper());
    }

    private List<CompanyTermsVO> fetchCompanyTerms() {
        return (List<CompanyTermsVO>) getJdbcTemplate().query(GET_COMPANY_TERMS_SQL, new CompanyTermsRowMapper());
    }

    private class CompanyTermsRowMapper implements RowMapper {
        /**
         * method to map the result to vo
         *
         * @param resultSet resultSet instance
         * @param i         i instance
         * @return UserVO as Object
         * @throws java.sql.SQLException on error
         */
        public Object mapRow(ResultSet resultSet, int i) throws SQLException {
            CompanyTermsVO companyTermsVO = new CompanyTermsVO();
            companyTermsVO.setTermsId(resultSet.getLong("Id"));
            companyTermsVO.setTermsAndConditions(resultSet.getString("Terms"));
            companyTermsVO.setCompanyDetails(resultSet.getString("CompanyDetails"));
            return companyTermsVO;
        }
    }
    private class CompanyRowMapper implements RowMapper {
        /**
         * method to map the result to vo
         *
         * @param resultSet resultSet instance
         * @param i         i instance
         * @return UserVO as Object
         * @throws java.sql.SQLException on error
         */
        public Object mapRow(ResultSet resultSet, int i) throws SQLException {
            CompanyTermsVO companyTermsVO = new CompanyTermsVO();
            companyTermsVO.setTermsId(resultSet.getLong("Id"));
            companyTermsVO.setCompanyDetails(resultSet.getString("CompanyDetails"));
            return companyTermsVO;
        }
    }

    private class TermsRowMapper implements RowMapper {
        /**
         * method to map the result to vo
         *
         * @param resultSet resultSet instance
         * @param i         i instance
         * @return UserVO as Object
         * @throws java.sql.SQLException on error
         */
        public Object mapRow(ResultSet resultSet, int i) throws SQLException {
            CompanyTermsVO companyTermsVO = new CompanyTermsVO();
            companyTermsVO.setTermsId(resultSet.getLong("Id"));
            companyTermsVO.setTermsAndConditions(resultSet.getString("Terms"));
            return companyTermsVO;
        }
    }
}
