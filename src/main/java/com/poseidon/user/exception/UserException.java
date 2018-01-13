package com.poseidon.user.exception;

/**
 * @author : Suraj Muraleedharan
 * Date: Nov 27, 2010
 * Time: 2:38:15 PM
 */
@SuppressWarnings("unused")
public class UserException extends Exception {

    /**
     * Exception type for Unknown user
     */
    public static String UNKNOWN_USER = "UNKNOWN_USER";

    /**
     * exception type for Invalid user
     */
    public static String INCORRECT_PASSWORD = "INCORRECT_PASSWORD";

    /**
     * exception type for all database related errors
     */
    public static String DATABASE_ERROR = "DATABASE_ERROR";

    public String ExceptionType;

    public UserException(String exceptionType) {
        this.ExceptionType = exceptionType;
    }

    public String getExceptionType() {
        return ExceptionType;
    }

    public void setExceptionType(String exceptionType) {
        ExceptionType = exceptionType;
    }
}
