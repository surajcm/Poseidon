package com.poseidon.user;

import com.poseidon.user.service.SecurityService;
import com.poseidon.user.service.UserService;
import com.poseidon.user.web.controller.HomeController;
import com.poseidon.user.web.controller.UserController;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
public class UserConfigurations {
    @Bean
    public UserController userController() {
        return new UserController(
                Mockito.mock(UserService.class));
    }

    @Bean
    public UserService userService() {
        return Mockito.mock(UserService.class);
    }

    @Bean
    public HomeController homeController() {
        return new HomeController(Mockito.mock(SecurityService.class));
    }
}
