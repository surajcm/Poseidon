package com.poseidon.user.validator;

import com.poseidon.user.domain.UserVO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {

    /**
     * default method overridden for matching the POJO.
     *
     * @param classInstance classInstance instance
     * @return boolean
     */
    @Override
    public boolean supports(final Class<?> classInstance) {
        return classInstance.equals(UserVO.class);
    }

    /**
     * validate method overridden from validator.
     *
     * @param userVo userVo the user instance
     * @param errors errors
     */
    @Override
    public void validate(final Object userVo, final Errors errors) {
        UserVO user = (UserVO) userVo;

        if (user.getName().trim().length() == 0) {
            errors.rejectValue("name", "error.required.user.name", "User Name is required");
        }
        if (user.getPassword().trim().length() == 0) {
            errors.rejectValue("password", "error.required.password", "Password is required");
        }
    }
}
