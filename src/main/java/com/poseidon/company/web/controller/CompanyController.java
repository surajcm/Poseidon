package com.poseidon.company.web.controller;

import com.poseidon.company.domain.CompanyTermsVO;
import com.poseidon.company.service.CompanyTermsService;
import com.poseidon.company.web.form.CompanyTermsForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.OffsetDateTime;
import java.time.ZoneId;

@Controller
@SuppressWarnings("unused")
public class CompanyController {
    private static final Logger LOG = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private CompanyTermsService companyTermsService;

    /**
     * list company details.
     *
     * @param companyTermsForm companyTermsForm
     * @return on error
     */
    @PostMapping("/company/Company.htm")
    public ModelAndView list(final CompanyTermsForm companyTermsForm) {
        LOG.info(" Inside Company method of CompanyTermsController ");
        CompanyTermsVO companyTermsVO = null;
        try {
            companyTermsVO = companyTermsService.listCompanyTerms();
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        if (companyTermsVO != null) {
            LOG.info(" companyTermsVO is {}", companyTermsVO);
            companyTermsForm.setCurrentCompanyTermsVO(companyTermsVO);
        } else {
            companyTermsForm.setCurrentCompanyTermsVO(new CompanyTermsVO());
        }
        companyTermsForm.setLoggedInRole(companyTermsForm.getLoggedInRole());
        companyTermsForm.setLoggedInUser(companyTermsForm.getLoggedInUser());
        return new ModelAndView("company/companyDetails", "companyTermsForm", companyTermsForm);
    }

    /**
     * update company details.
     *
     * @param companyTermsForm companyTermsForm
     * @return view
     */
    @PostMapping("/company/updateCompanyDetails.htm")
    public ModelAndView updateCompanyDetails(final CompanyTermsForm companyTermsForm) {
        LOG.info(" Inside editTerms method of CompanyTermsController ");
        if (companyTermsForm.getCurrentCompanyTermsVO() != null) {
            companyTermsForm.getCurrentCompanyTermsVO().setModifiedBy(companyTermsForm.getLoggedInUser());
            companyTermsForm.getCurrentCompanyTermsVO().setModifiedDate(OffsetDateTime.now(ZoneId.systemDefault()));
        }
        CompanyTermsVO companyTermsVO = null;
        try {
            companyTermsVO = companyTermsService.updateCompanyDetails(companyTermsForm.getCurrentCompanyTermsVO());
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        if (companyTermsVO != null) {
            LOG.info(" companyTermsVO is {}", companyTermsVO);
            companyTermsForm.setCurrentCompanyTermsVO(companyTermsVO);
        } else {
            companyTermsForm.setCurrentCompanyTermsVO(new CompanyTermsVO());
        }
        companyTermsForm.setLoggedInRole(companyTermsForm.getLoggedInRole());
        companyTermsForm.setLoggedInUser(companyTermsForm.getLoggedInUser());
        return new ModelAndView("company/TermsList", "companyTermsForm", companyTermsForm);
    }
}
