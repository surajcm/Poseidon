package com.poseidon.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@SuppressWarnings("unused")
public class SecurityService {
    private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);
    private final UserDetailsService userDetailsService;

    public SecurityService(final UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String findLoggedInUsername() {
        String username = null;
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails details) {
            username = details.getUsername();
        }
        return username;
    }

    public boolean isAuthenticated() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null ||
                AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    /**
     * This is used in the navbar to check whether the user is having a specific role (eg. ADMIN, USER)
     * @param role role of the user
     * @return boolean true if is correct
     */
    public boolean hasRole(final String role) {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities();
        return authorities.stream().anyMatch(a -> a.getAuthority().equals(role));
    }
}
