package com.poseidon.user.web.controller;

import com.poseidon.user.domain.UserVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @ModelAttribute("userVO")
    public UserVO userVO() {
        return new UserVO();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }
}