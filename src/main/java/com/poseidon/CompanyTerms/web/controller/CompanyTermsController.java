package com.poseidon.CompanyTerms.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.poseidon.CompanyTerms.delegate.CompanyTermsDelegate;
import com.poseidon.CompanyTerms.web.form.CompanyTermsForm;
import com.poseidon.CompanyTerms.domain.CompanyTermsVO;

import java.util.Date;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 10:44:08 PM
 */
@Controller
public class CompanyTermsController {

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

    @RequestMapping(value = "/company/List.htm", method = RequestMethod.POST)
    public ModelAndView List(CompanyTermsForm companyTermsForm) {
        log.info(" Inside List method of CompanyTermsController ");
        log.info(" form details are " + companyTermsForm);

        CompanyTermsVO companyTermsVO = null;
        try {
            companyTermsVO = getCompanyTermsDelegate().listCompanyTerms();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (companyTermsVO != null) {
            log.info(" companyTermsVO is " + companyTermsVO);
            companyTermsForm.setCurrentCompanyTermsVO(companyTermsVO);
        } else {
            companyTermsForm.setCurrentCompanyTermsVO(new CompanyTermsVO());
        }
        companyTermsForm.setLoggedInRole(companyTermsForm.getLoggedInRole());
        companyTermsForm.setLoggedInUser(companyTermsForm.getLoggedInUser());
        return new ModelAndView("company/TermsList", "companyTermsForm", companyTermsForm);
    }

    @RequestMapping(value = "/company/updateCompanyDetails.htm", method = RequestMethod.POST)
    public ModelAndView updateCompanyDetails(CompanyTermsForm companyTermsForm) {
        log.info(" Inside editTerms method of CompanyTermsController ");
        log.info(" form details are " + companyTermsForm);

        companyTermsForm.getCurrentCompanyTermsVO().setModifiedBy(companyTermsForm.getLoggedInUser());
        companyTermsForm.getCurrentCompanyTermsVO().setModifiedDate(new Date());
        try {
            getCompanyTermsDelegate().updateCompanyDetails(companyTermsForm.getCurrentCompanyTermsVO());
        } catch (Exception e) {
            e.printStackTrace();
        }

        CompanyTermsVO companyTermsVO = null;
        try {
            companyTermsVO = getCompanyTermsDelegate().listCompanyTerms();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (companyTermsVO != null) {
            log.info(" companyTermsVO is " + companyTermsVO);
            companyTermsForm.setCurrentCompanyTermsVO(companyTermsVO);
        } else {
            companyTermsForm.setCurrentCompanyTermsVO(new CompanyTermsVO());
        }

        companyTermsForm.setLoggedInRole(companyTermsForm.getLoggedInRole());
        companyTermsForm.setLoggedInUser(companyTermsForm.getLoggedInUser());
        return new ModelAndView("company/TermsList", "companyTermsForm", companyTermsForm);
    }


}
