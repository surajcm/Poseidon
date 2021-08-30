package com.poseidon.company.web.controller;

import com.poseidon.company.domain.CompanyTermsVO;
import com.poseidon.company.service.CompanyTermsService;
import com.poseidon.company.web.form.CompanyTermsForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.OffsetDateTime;
import java.time.ZoneId;
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
     * @param companyTermsForm companyTermsForm
     * @return on error
     */
    @PostMapping("/company/Company.htm")
    public ModelAndView list(final CompanyTermsForm companyTermsForm) {
        LOG.info(" Inside Company method of CompanyTermsController ");
        var companyTermsVO = fetchCompanyTerms();
        companyTermsVO.ifPresent(termsVO -> setCompanyTerms(companyTermsForm, termsVO));
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
        var companyTermsVO = updateCompanyTermsVO(companyTermsForm);
        companyTermsVO.ifPresent(termsVO -> setCompanyTerms(companyTermsForm, termsVO));
        return new ModelAndView("company/companyDetails", "companyTermsForm", companyTermsForm);
    }

    private Optional<CompanyTermsVO> fetchCompanyTerms() {
        return companyTermsService.listCompanyTerms();
    }

    private void setCompanyTerms(final CompanyTermsForm companyTermsForm, final CompanyTermsVO companyTermsVO) {
        if (companyTermsVO != null) {
            LOG.info(" companyTermsVO is {}", companyTermsVO);
            companyTermsForm.setCurrentCompanyTermsVO(companyTermsVO);
        } else {
            companyTermsForm.setCurrentCompanyTermsVO(new CompanyTermsVO());
        }
    }

    private Optional<CompanyTermsVO> updateCompanyTermsVO(final CompanyTermsForm companyTermsForm) {
        if (companyTermsForm.getCurrentCompanyTermsVO() != null) {
            companyTermsForm.getCurrentCompanyTermsVO().setModifiedBy(
                    companyTermsForm.getLoggedInUser());
            companyTermsForm.getCurrentCompanyTermsVO().setModifiedDate(
                    OffsetDateTime.now(ZoneId.systemDefault()));
        }
        return companyTermsService.updateCompanyDetails(companyTermsForm.getCurrentCompanyTermsVO());
    }
}
