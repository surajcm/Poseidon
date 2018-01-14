package com.poseidon.company.dao.impl;

import com.poseidon.company.dao.CompanyTermsDAO;
import com.poseidon.company.domain.CompanyTermsVO;
import com.poseidon.company.exception.CompanyTermsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * user: Suraj
 * Date: Jun 2, 2012
 * Time: 10:00:05 PM
 */
@Repository
public class CompanyTermsDAOImpl implements CompanyTermsDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String GET_COMPANY_TERMS_SQL = "SELECT id, terms, companyName, companyAddress, companyPhone, " +
            " companyEmail, companyWebsite, vat_tin, cst_tin FROM companyterms ;";
    private final String UPDATE_TERMS_SQL = "update companyterms set terms = ?,companyAddress = ?, companyName = ?, " +
            " companyPhone = ?, companyEmail = ? , companyWebsite = ?, vat_tin = ? , cst_tin = ?, modifiedOn = ?, modifiedBy = ?  where id = 1 ;";


    public CompanyTermsVO listCompanyTerms() throws CompanyTermsException {
        CompanyTermsVO companyTermsVO = null;
        try {
            List<CompanyTermsVO> companyTermsVOs = fetchCompanyTerms();
            if (companyTermsVOs != null && companyTermsVOs.size() > 0) {
                companyTermsVO = companyTermsVOs.get(0);
            }
        } catch (DataAccessException e) {
            throw new CompanyTermsException(CompanyTermsException.DATABASE_ERROR);
        }
        return companyTermsVO;
    }

    public void updateCompanyDetails(CompanyTermsVO companyTermsVO) throws CompanyTermsException {

        Object[] parameters = new Object[]{companyTermsVO.getCompanyTerms(),
                companyTermsVO.getCompanyAddress(),
                companyTermsVO.getCompanyName(),
                companyTermsVO.getCompanyPhoneNumber(),
                companyTermsVO.getCompanyEmail(),
                companyTermsVO.getCompanyWebsite(),
                companyTermsVO.getCompanyVATTIN(),
                companyTermsVO.getCompanyCSTTIN(),
                companyTermsVO.getModifiedDate(),
                companyTermsVO.getModifiedBy()};

        try {
            jdbcTemplate.update(UPDATE_TERMS_SQL, parameters);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new CompanyTermsException(CompanyTermsException.DATABASE_ERROR);
        }
    }

    private List<CompanyTermsVO> fetchCompanyTerms() {
        return (List<CompanyTermsVO>) jdbcTemplate.query(GET_COMPANY_TERMS_SQL, new CompanyTermsRowMapper());
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
            companyTermsVO.setId(resultSet.getLong("id"));
            companyTermsVO.setCompanyName(resultSet.getString("companyName"));
            companyTermsVO.setCompanyAddress(resultSet.getString("companyAddress"));
            companyTermsVO.setCompanyPhoneNumber(resultSet.getString("companyPhone"));
            companyTermsVO.setCompanyEmail(resultSet.getString("companyEmail"));
            companyTermsVO.setCompanyWebsite(resultSet.getString("companyWebsite"));
            companyTermsVO.setCompanyTerms(resultSet.getString("terms"));
            companyTermsVO.setCompanyVATTIN(resultSet.getString("vat_tin"));
            companyTermsVO.setCompanyCSTTIN(resultSet.getString("cst_tin"));
            return companyTermsVO;
        }
    }

}
