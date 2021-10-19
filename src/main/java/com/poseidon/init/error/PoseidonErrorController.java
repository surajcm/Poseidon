package com.poseidon.init.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PoseidonErrorController implements ErrorController {
    @GetMapping("/error")
    public String handleError() {
        //do something like logging
        return "ErrorPage";
    }

    @PostMapping("/error")
    public String handleErrorPost() {
        //do something like logging
        return "ErrorPage";
    }
}
