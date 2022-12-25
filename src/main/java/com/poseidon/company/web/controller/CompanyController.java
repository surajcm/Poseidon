package com.poseidon.company.web.controller;

import com.poseidon.company.dao.entities.CompanyTerms;
import com.poseidon.company.service.CompanyService;
import com.poseidon.user.service.UserService;
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
    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    private final CompanyService companyService;

    private final UserService userService;

    public CompanyController(final CompanyService companyService,
                             final UserService userService) {
        this.companyService = companyService;
        this.userService = userService;
    }

    /**
     * list company details.
     *
     * @return on error
     */
    @PostMapping("/company/company")
    public String list(final Model model) {
        logger.info("Inside Company method of CompanyTermsController");
        var companyCode = activeCompanyCode();
        logger.info("companyCode is {}", companyCode);
        var companyTerms = fetchCompanyTerms(companyCode);
        model.addAttribute("companyTerms", companyTerms);
        return "company/companyDetails";
    }

    private Optional<CompanyTerms> fetchCompanyTerms(final String companyCode) {
        return companyService.listCompanyTerms(companyCode);
    }

    /**
     * update company details.
     *
     * @return view
     */
    @PostMapping("/company/updateCompanyDetails")
    public String updateCompanyDetails(@ModelAttribute final CompanyTerms companyTerms,
                                       final Model model) {
        logger.info(" Inside editTerms method of CompanyTermsController");
        var updatedCompanyTerms = updateCompanyTermsVO(companyTerms);
        model.addAttribute("companyTerms", updatedCompanyTerms);
        return "company/companyDetails";
    }

    private Optional<CompanyTerms> updateCompanyTermsVO(final CompanyTerms updated) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            var username = auth.getName();
            updated.setModifiedBy(username);
        }
        return companyService.updateCompanyDetails(updated);
    }

    private String activeCompanyCode() {
        var currentLoggedInUser = currentLoggedInUser();
        var user = userService.findUserFromName(currentLoggedInUser);
        return user.getCompanyCode();
    }

    private String currentLoggedInUser() {
        var username = "";
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            username = auth.getName();
        }
        return username;
    }

}
