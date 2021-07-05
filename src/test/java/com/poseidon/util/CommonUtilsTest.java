package com.poseidon.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CommonUtilsTest {

    @Test
    void verifySanitizedString() {
        String params = "hello";
        Assertions.assertEquals(params, CommonUtils.sanitizedString("hello"));
    }

}