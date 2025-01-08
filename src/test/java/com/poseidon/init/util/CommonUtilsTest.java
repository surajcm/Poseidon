package com.poseidon.init.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CommonUtilsTest {

    @Test
    void verifySanitizedString() {
        String params = "hello";
        Assertions.assertEquals(params, CommonUtils.sanitizedString("hello"),
                "Sanitized string should be equal to hello");
    }

}