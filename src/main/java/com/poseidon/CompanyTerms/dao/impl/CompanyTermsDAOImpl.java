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
}
