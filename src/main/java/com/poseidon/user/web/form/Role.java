package com.poseidon.user.web.form;

import java.util.Arrays;
import java.util.List;

public enum Role {
    ADMIN,
    GUEST;

    public static List<String> populateRoles() {
        return Arrays.stream(values()).map(Enum::name).toList();
    }
}
