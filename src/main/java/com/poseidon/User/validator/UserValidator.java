package com.poseidon.User.validator;

import com.poseidon.User.domain.UserVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author : Suraj Muraleedharan
 * Date: Nov 27, 2010
 * Time: 2:38:15 PM
 */
public class UserValidator implements Validator {
    /**
     * logger for validator
     */
    private final Log log = LogFactory.getLog(UserValidator.class);

    /**
     * default method overridden for matching the POJO
     * @param aClass aClass instance
     * @return boolean
     */
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UserVO.class);
    }

    /**
     * validate method overridden from validator
     * @param o o the user instance
     * @param errors errors
     */
    public void validate(Object o, Errors errors) {
        log.info(" Inside the validate method");
        UserVO user = (UserVO) o;

        if (user.getName().trim().length() == 0) {
            errors.rejectValue("name", "error.required.user.name", "User Name is required");
        }
        if (user.getPassword().trim().length() == 0) {
            errors.rejectValue("password", "error.required.password", "Password is required");
        }
    }
}
