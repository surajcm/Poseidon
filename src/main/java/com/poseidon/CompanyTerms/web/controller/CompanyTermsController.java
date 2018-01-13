package com.poseidon.CompanyTerms.web.controller;

import com.poseidon.CompanyTerms.domain.CompanyTermsVO;
import com.poseidon.CompanyTerms.service.CompanyTermsService;
import com.poseidon.CompanyTerms.web.form.CompanyTermsForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 10:44:08 PM
 */
@Controller
public class CompanyTermsController {
    private static final Logger LOG = LoggerFactory.getLogger(CompanyTermsController.class);

    private CompanyTermsService companyTermsService;


    public CompanyTermsService getCompanyTermsService() {
        return companyTermsService;
    }

    public void setCompanyTermsService(CompanyTermsService companyTermsService) {
        this.companyTermsService = companyTermsService;
    }



    @RequestMapping(value = "/company/List.htm", method = RequestMethod.POST)
    public ModelAndView List(CompanyTermsForm companyTermsForm) {
        LOG.info(" Inside List method of CompanyTermsController ");
        LOG.info(" form details are " + companyTermsForm);

        CompanyTermsVO companyTermsVO = null;
        try {
            companyTermsVO = getCompanyTermsService().listCompanyTerms();
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
        }
        if (companyTermsVO != null) {
            LOG.info(" companyTermsVO is " + companyTermsVO);
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
        LOG.info(" Inside editTerms method of CompanyTermsController ");
        LOG.info(" form details are " + companyTermsForm);

        companyTermsForm.getCurrentCompanyTermsVO().setModifiedBy(companyTermsForm.getLoggedInUser());
        companyTermsForm.getCurrentCompanyTermsVO().setModifiedDate(new Date());
        try {
            getCompanyTermsService().updateCompanyDetails(companyTermsForm.getCurrentCompanyTermsVO());
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
        }

        CompanyTermsVO companyTermsVO = null;
        try {
            companyTermsVO = getCompanyTermsService().listCompanyTerms();
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
        }
        if (companyTermsVO != null) {
            LOG.info(" companyTermsVO is " + companyTermsVO);
            companyTermsForm.setCurrentCompanyTermsVO(companyTermsVO);
        } else {
            companyTermsForm.setCurrentCompanyTermsVO(new CompanyTermsVO());
        }

        companyTermsForm.setLoggedInRole(companyTermsForm.getLoggedInRole());
        companyTermsForm.setLoggedInUser(companyTermsForm.getLoggedInUser());
        return new ModelAndView("company/TermsList", "companyTermsForm", companyTermsForm);
    }


}
