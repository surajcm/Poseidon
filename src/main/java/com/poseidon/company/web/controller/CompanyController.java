package com.poseidon.company.web.controller;

import com.poseidon.company.domain.CompanyTermsVO;
import com.poseidon.company.service.CompanyTermsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String list(final Model model) {
        LOG.info("Inside Company method of CompanyTermsController");
        var companyTermsVO = fetchCompanyTerms();
        model.addAttribute("companyTermsVO", companyTermsVO);
        return "company/companyDetails";
    }

    /**
     * update company details.
     *
     * @return view
     */
    @PostMapping("/company/updateCompanyDetails.htm")
    public String updateCompanyDetails(@ModelAttribute final CompanyTermsVO companyTermsVO, final Model model) {
        LOG.info(" Inside editTerms method of CompanyTermsController");
        var updatedCompanyTermsVO = updateCompanyTermsVO(companyTermsVO);
        model.addAttribute("companyTermsVO", updatedCompanyTermsVO);
        return "company/companyDetails";
    }

    private Optional<CompanyTermsVO> fetchCompanyTerms() {
        return companyTermsService.listCompanyTerms();
    }

    private Optional<CompanyTermsVO> updateCompanyTermsVO(final CompanyTermsVO companyTermsVO) {
        if (companyTermsVO != null) {
            var auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                var username = auth.getName();
                companyTermsVO.setModifiedBy(username);
            }
        }
        return companyTermsService.updateCompanyDetails(companyTermsVO);
    }
}
