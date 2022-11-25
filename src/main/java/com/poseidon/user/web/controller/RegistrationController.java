package com.poseidon.user.web.controller;

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


    public RegistrationController(final UserService userService) {
        this.userService = userService;
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
        logger.info("Submitted registration: {}", userVO);
        //todo: add support for all roles
        userVO.setRole("GUEST");
        userService.save(userVO, "signup");
        return "redirect:/registration?success";
    }
}