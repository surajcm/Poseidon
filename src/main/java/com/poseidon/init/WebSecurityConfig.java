package com.poseidon.init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {
    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(final UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http,
                                           final HandlerMappingIntrospector introspect) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        var mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspect);
        for (var paths: matchingPaths()) {
            http.authorizeHttpRequests(auth -> auth
                    .requestMatchers(mvcMatcherBuilder.pattern(paths)).permitAll()
            );
        }
        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());

        http.formLogin((formLogin) -> {
            try {
                formLogin.loginPage("/login")
                .defaultSuccessUrl("/", true).permitAll();
            } catch (Exception ex) {
                //todo : clean up
                throw new RuntimeException(ex);
            }
        });
        http.headers((headers) ->
                headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        http.logout(LogoutConfigurer::permitAll);
        http.requiresChannel(c -> c.requestMatchers(r ->
                r.getHeader("X-Forwarded-Proto") != null).requiresSecure());
        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(final HttpSecurity http,
                                             final BCryptPasswordEncoder bCryptPasswordEncoder)
            throws Exception {
        var managerBuilder =  http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
        return managerBuilder.and().build();
    }

    private String[] matchingPaths() {
        return new String[] {"/resources/**",
                "/registration",
                "/css/**", "/js/**", "/img/**",
                "/h2-console/**",
                "/console/**"
        };
    }
}