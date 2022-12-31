package com.poseidon.user.web.controller;

import com.poseidon.user.service.SecurityService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private static final String USER_LOG_IN = "login";
    private static final String DANGER = "danger";

    private static final String MESSAGE = "message";
    private final SecurityService securityService;


    public HomeController(final SecurityService securityService) {
        this.securityService = securityService;
    }

    /**
     * Used in automatic redirect to Log in screen.
     *
     * @return MainPage screen
     */
    @GetMapping("/")
    public String welcome() {
        return "MainPage";
    }

    /**
     * log in.
     *
     * @param model  Model
     * @param error  String
     * @param logout String
     * @return String
     */
    @GetMapping("/login")
    public String login(final Model model, final String error, final String logout) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }
        if (error != null) {
            model.addAttribute(DANGER, "Your username or password is invalid.");
        }
        if (logout != null) {
            model.addAttribute(MESSAGE, "You have been logged out successfully.");
        }
        return USER_LOG_IN;
    }


    /**
     * Screen to home.
     *
     * @return to home screen
     */
    @PostMapping("/home")
    public String toHome() {
        logger.info("Inside ToHome method of user controller");
        return "MainPage";
    }


    /**
     * Screen to log out.
     *
     * @return to logout screen
     */
    @PostMapping("/logMeOut")
    public String logMeOut(final HttpServletRequest request) {
        logger.info("Inside LogMeOut method of user controller");
        var session = request.getSession(false);
        SecurityContextHolder.clearContext();
        if (session != null) {
            session.invalidate();
        }
        return USER_LOG_IN;
    }

    @PostMapping("/user/passwordReset")
    public String reset() {
        logger.info("Password reset view of user controller");
        return "user/PasswordReset";
    }
}
