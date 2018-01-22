package com.poseidon.user.service.impl;

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}
