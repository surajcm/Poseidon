package com.poseidon.company.web.controller;

import com.poseidon.company.dao.entities.CompanyTerms;
import com.poseidon.company.service.CompanyService;
import com.poseidon.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String myCompany(final Model model) {
        var companyCode = activeCompanyCode();
        logger.info("companyCode is {}", companyCode);
        var companyTerms = companyService.listCompanyTerms(companyCode);
        model.addAttribute("companyTerms", companyTerms);
        return "company/companyDetails";
    }

    /**
     * list company details.
     *
     * @return on error
     */
    @RequestMapping(value = "/company/companies", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(final Model model) {
        var companies = companyService.listCompanies();
        model.addAttribute("companies", companies);
        return "company/companies";
    }

    @GetMapping("/company/delete/{id}")
    public String deleteCompany(final @PathVariable("id") Long id,
                                final Model model,
                                final RedirectAttributes redirectAttributes) {
        companyService.deleteCompany(id);
        redirectAttributes.addFlashAttribute("message", "Company details deleted successfully");
        return "redirect:/company/companies";
    }

    @GetMapping("/company/edit/{id}")
    public String editCompany(final @PathVariable("id") Long id, final Model model) {
        var companyTerms = companyService.getCompanyTerms(id);
        model.addAttribute("companyTerms", companyTerms);
        return "company/companyDetails";
    }

    /**
     * update company details.
     *
     * @return view
     */
    @PostMapping("/company/update")
    public String updateCompanyDetails(@ModelAttribute final CompanyTerms companyTerms,
                                       final Model model, final RedirectAttributes redirectAttributes) {
        logger.info("Inside editTerms method of CompanyTermsController, current company details are {}", companyTerms);
        var updatedCompanyTerms = updateCompanyTermsVO(companyTerms);
        model.addAttribute("companyTerms", updatedCompanyTerms);
        redirectAttributes.addFlashAttribute("message", "Company details updated successfully");
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
