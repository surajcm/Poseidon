package com.poseidon.company.web.controller;

import com.poseidon.company.domain.CompanyTermsVO;
import com.poseidon.company.service.CompanyTermsService;
import com.poseidon.company.web.form.CompanyTermsForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@SuppressWarnings("unused")
public class CompanyController {
    private static final Logger LOG = LoggerFactory.getLogger(CompanyController.class);

    private final CompanyTermsService companyTermsService;

    public CompanyController(final CompanyTermsService companyTermsService) {
        this.companyTermsService = companyTermsService;
    }

    /**
     * list company details.
     *
     * @return on error
     */
    @PostMapping("/company/Company.htm")
    public ModelAndView list() {
        LOG.info("Inside Company method of CompanyTermsController");
        var companyTermsVO = fetchCompanyTerms();
        CompanyTermsForm companyTermsForm = new CompanyTermsForm();
        companyTermsVO.ifPresent(companyTermsForm::setCurrentCompanyTermsVO);
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
        LOG.info(" Inside editTerms method of CompanyTermsController");
        var companyTermsVO = updateCompanyTermsVO(companyTermsForm);
        companyTermsVO.ifPresent(companyTermsForm::setCurrentCompanyTermsVO);
        return new ModelAndView("company/companyDetails", "companyTermsForm", companyTermsForm);
    }

    private Optional<CompanyTermsVO> fetchCompanyTerms() {
        return companyTermsService.listCompanyTerms();
    }

    private Optional<CompanyTermsVO> updateCompanyTermsVO(final CompanyTermsForm companyTermsForm) {
        if (companyTermsForm.getCurrentCompanyTermsVO() != null) {
            var auth = SecurityContextHolder.getContext().getAuthentication();
            var username = auth.getName();
            companyTermsForm.getCurrentCompanyTermsVO().setModifiedBy(username);
        }
        return companyTermsService.updateCompanyDetails(companyTermsForm.getCurrentCompanyTermsVO());
    }
}
