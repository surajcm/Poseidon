package com.poseidon.user;

import com.poseidon.user.service.UserService;
import com.poseidon.user.service.impl.SecurityService;
import com.poseidon.user.web.controller.UserController;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
public class UserConfigurations {
    @Bean
    public UserController userController() {
        return new UserController();
    }

    @Bean
    public UserService userService() {
        return Mockito.mock(UserService.class);
    }

    @Bean
    public SecurityService securityService() {
        return Mockito.mock(SecurityService.class);
    }
}
