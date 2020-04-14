package com.poseidon.user.exception;

@SuppressWarnings("unused")
public class UserException extends Exception {

    /**
     * Exception type for Unknown user.
     */
    public static final String UNKNOWN_USER = "UNKNOWN_USER";

    /**
     * exception type for Invalid user.
     */
    public static final String INCORRECT_PASSWORD = "INCORRECT_PASSWORD";

    public static final long serialVersionUID = 4328749;

    /**
     * exception type for all database related errors.
     */
    public static final String DATABASE_ERROR = "DATABASE_ERROR";

    public String exceptionType;

    public UserException(final String exceptionType) {
        super();
        this.exceptionType = exceptionType;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(final String exceptionType) {
        this.exceptionType = exceptionType;
    }
}
