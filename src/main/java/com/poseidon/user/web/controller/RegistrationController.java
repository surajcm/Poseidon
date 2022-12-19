package com.poseidon.user.web.controller;

import com.poseidon.company.service.CompanyService;
import com.poseidon.user.dao.entities.Role;
import com.poseidon.user.dao.entities.User;
import com.poseidon.user.domain.UserVO;
import com.poseidon.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private final UserService userService;
    private final CompanyService companyService;

    public RegistrationController(final UserService userService,
                                  final CompanyService companyService) {
        this.userService = userService;
        this.companyService = companyService;
    }

    @ModelAttribute("userVO")
    public UserVO userVO() {
        return new UserVO();
    }

    @GetMapping
    public String showRegistrationForm() {
        logger.info("Going to registration page !!");
        return "registration";
    }

    @PostMapping
    public String registerUser(final UserVO userVO) {
        userVO.addRole(new Role(2L));
        logger.info("Submitted registration: {}", userVO);
        if (companyService.isValidCompanyCode(userVO.getCompanyCode())) {
            var user = convertToUser(userVO, "signup");
            userService.save(user);
        } else {
            logger.error("Invalid companyCode found, {}", userVO.getCompanyCode());
            return "redirect:/registration?failure";
        }
        return "redirect:/registration?success";
    }

    private User convertToUser(final UserVO userVO, final String currentLoggedInUser) {
        var user = new User();
        user.setName(userVO.getName());
        user.setEmail(userVO.getEmail());
        user.setPassword(userVO.getPassword());
        user.setEnabled(userVO.getEnabled());
        user.setRoles(userVO.getRoles());
        user.setCompanyCode(userVO.getCompanyCode());
        user.setCreatedBy(currentLoggedInUser);
        user.setModifiedBy(currentLoggedInUser);
        return user;
    }
}