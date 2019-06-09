package com.poseidon.user.validator;

import com.poseidon.user.domain.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author : Suraj Muraleedharan
 * Date: Nov 27, 2010
 * Time: 2:38:15 PM
 */
public class UserValidator implements Validator {
    /**
     * logger for validator.
     */
    private static final Logger logger = LoggerFactory.getLogger(UserValidator.class);

    /**
     * default method overridden for matching the POJO.
     *
     * @param classInstance classInstance instance
     * @return boolean
     */
    @Override
    public boolean supports(Class<?> classInstance) {
        return classInstance.equals(UserVO.class);
    }

    /**
     * validate method overridden from validator.
     *
     * @param userVo      userVo the user instance
     * @param errors errors
     */
    @Override
    public void validate(Object userVo, Errors errors) {
        logger.info(" Inside the validate method");
        UserVO user = (UserVO) userVo;

        if (user.getName().trim().length() == 0) {
            errors.rejectValue("name", "error.required.user.name", "user Name is required");
        }
        if (user.getPassword().trim().length() == 0) {
            errors.rejectValue("password", "error.required.password", "Password is required");
        }
    }
}
