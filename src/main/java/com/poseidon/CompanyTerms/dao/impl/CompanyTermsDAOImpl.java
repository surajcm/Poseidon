package com.poseidon.CompanyTerms.dao.impl;

import com.poseidon.CompanyTerms.dao.CompanyTermsDAO;
import com.poseidon.CompanyTerms.domain.CompanyTermsVO;
import com.poseidon.CompanyTerms.exception.CompanyTermsException;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 10:00:05 PM
 */
public class CompanyTermsDAOImpl extends JdbcDaoSupport implements CompanyTermsDAO {

    public List<CompanyTermsVO> listCompanyTerms() throws CompanyTermsException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
