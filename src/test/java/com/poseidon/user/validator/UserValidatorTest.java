package com.poseidon.user.validator;

import com.poseidon.user.domain.UserVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UserValidatorTest {
    private final UserValidator validator = new UserValidator();

    @Test
    void verifyValidatorSupports() {
        var userVO = Mockito.mock(UserVO.class);
        Assertions.assertFalse(validator.supports(userVO.getClass()));
        Assertions.assertTrue(validator.supports(UserVO.class));
    }
}