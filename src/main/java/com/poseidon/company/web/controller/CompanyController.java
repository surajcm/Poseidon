package com.poseidon.company.web.controller;

import com.poseidon.company.domain.CompanyTermsVO;
import com.poseidon.company.service.CompanyService;
import com.poseidon.user.dao.entities.User;
import com.poseidon.user.domain.UserVO;
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
        var companyTermsVO = fetchCompanyTerms(companyCode);
        model.addAttribute("companyTermsVO", companyTermsVO);
        return "company/companyDetails";
    }

    /**
     * update company details.
     *
     * @return view
     */
    @PostMapping("/company/updateCompanyDetails")
    public String updateCompanyDetails(@ModelAttribute final CompanyTermsVO companyTermsVO, final Model model) {
        logger.info(" Inside editTerms method of CompanyTermsController");
        var updatedCompanyTermsVO = updateCompanyTermsVO(companyTermsVO);
        model.addAttribute("companyTermsVO", updatedCompanyTermsVO);
        return "company/companyDetails";
    }

    private Optional<CompanyTermsVO> fetchCompanyTerms(final String companyCode) {
        return companyService.listCompanyTerms(companyCode);
    }

    private Optional<CompanyTermsVO> updateCompanyTermsVO(final CompanyTermsVO companyTermsVO) {
        if (companyTermsVO != null) {
            var auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                var username = auth.getName();
                companyTermsVO.setModifiedBy(username);
            }
        }
        return companyService.updateCompanyDetails(companyTermsVO);
    }

    private String activeCompanyCode() {
        var currentLoggedInUser = currentLoggedInUser();
        var user = convertToUserVO(userService.findUserFromName(currentLoggedInUser));
        return user.getCompanyCode();
    }

    private UserVO convertToUserVO(final User user) {
        var userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setName(user.getName());
        userVO.setEmail(user.getEmail());
        userVO.setPassword(user.getPassword());
        userVO.setRoles(user.getRoles());
        userVO.setCompanyCode(user.getCompanyCode());
        userVO.setEnabled(user.getEnabled());
        return userVO;
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
