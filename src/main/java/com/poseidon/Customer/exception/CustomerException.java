package com.poseidon.Customer.exception;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 10:46:40 PM
 */
public class CustomerException extends Exception{
    /**
     * exception type for all database related errors
     */
    public static String DATABASE_ERROR = "DATABASE_ERROR";

    public String ExceptionType;

    public CustomerException(String exceptionType) {
        this.ExceptionType = exceptionType;
    }

    public String getExceptionType() {
        return ExceptionType;
    }

    public void setExceptionType(String exceptionType) {
        ExceptionType = exceptionType;
    }
}
