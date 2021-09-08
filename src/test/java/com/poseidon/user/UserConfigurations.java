package com.poseidon.user;

import com.poseidon.user.service.UserService;
import com.poseidon.user.service.SecurityService;
import com.poseidon.user.web.controller.UserController;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
public class UserConfigurations {
    @Bean
    public UserController userController() {
        return new UserController(
                Mockito.mock(UserService.class),
                Mockito.mock(SecurityService.class));
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
