package com.poseidon.company.exception;

/**
 * user: Suraj
 * Date: Jun 2, 2012
 * Time: 10:42:56 PM
 */
public class CompanyTermsException extends Exception{
    /**
     * exception type for all database related errors
     */
    public static String DATABASE_ERROR = "DATABASE_ERROR";

    public String ExceptionType;

    public CompanyTermsException(String exceptionType) {
        this.ExceptionType = exceptionType;
    }

    public String getExceptionType() {
        return ExceptionType;
    }

    public void setExceptionType(String exceptionType) {
        ExceptionType = exceptionType;
    }
}
