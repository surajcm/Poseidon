package com.poseidon.customer.exception;

/**
 * user: Suraj
 * Date: Jun 2, 2012
 * Time: 10:46:40 PM
 */
public class CustomerException extends Exception {
    /**
     * exception type for all database related errors
     */
    public static final String DATABASE_ERROR = "DATABASE_ERROR";

    private String exceptionType;
    public static final long serialVersionUID = 4328743;

    public CustomerException(String exceptionType) {
        super();
        this.exceptionType = exceptionType;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }
}
