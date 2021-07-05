package com.poseidon.user.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("unused")
public class SecurityServiceImpl implements SecurityService {

    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);
    @Autowired
    private WebSecurityConfigurerAdapter webSecurityConfigurerAdapter;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public String findLoggedInUsername() {
        String username = null;
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails details) {
            username = details.getUsername();
        }
        return username;
    }

    @Override
    public void autologin(final String username, final String password) {
        var userDetails = userDetailsService.loadUserByUsername(username);
        var token = new UsernamePasswordAuthenticationToken(
                userDetails, password, userDetails.getAuthorities());
        try {
            webSecurityConfigurerAdapter.authenticationManagerBean()
                    .authenticate(token);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        if (token.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(token);
        }
    }
}
