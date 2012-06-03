package com.poseidon.CompanyTerms.web.controller;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.poseidon.CompanyTerms.delegate.CompanyTermsDelegate;
import com.poseidon.CompanyTerms.web.form.CompanyTermsForm;
import com.poseidon.CompanyTerms.domain.CompanyTermsVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 10:44:08 PM
 */
public class CompanyTermsController extends MultiActionController {

    /**
     * CustomerDelegate instance
     */
    private CompanyTermsDelegate companyTermsDelegate;

    /**
     * logger for user controller
     */
    private final Log log = LogFactory.getLog(CompanyTermsController.class);

    public CompanyTermsDelegate getCompanyTermsDelegate() {
        return companyTermsDelegate;
    }

    public void setCompanyTermsDelegate(CompanyTermsDelegate companyTermsDelegate) {
        this.companyTermsDelegate = companyTermsDelegate;
    }

    public ModelAndView List(HttpServletRequest request,
                             HttpServletResponse response, CompanyTermsForm companyTermsForm) {
        log.info(" Inside List method of CompanyTermsController ");
        log.info(" form details are " + companyTermsForm);

        List<CompanyTermsVO> companyTermsVOs = null;
        try {
            companyTermsVOs = getCompanyTermsDelegate().listCompanyTerms();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (companyTermsVOs != null) {
            for (CompanyTermsVO companyTermsVO : companyTermsVOs) {
                log.info(" companyTermsVO is " + companyTermsVO);
            }
            companyTermsForm.setCompanyTermsVOs(companyTermsVOs);
        }
        companyTermsForm.setSearchCompanyTermsVO(new CompanyTermsVO());
        companyTermsForm.setLoggedInRole(companyTermsForm.getLoggedInRole());
        companyTermsForm.setLoggedInUser(companyTermsForm.getLoggedInUser());
        return new ModelAndView("company/TermsList", "companyTermsForm", companyTermsForm);
    }
}
