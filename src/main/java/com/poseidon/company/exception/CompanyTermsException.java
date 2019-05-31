package com.poseidon.company.exception;

/**
 * user: Suraj
 * Date: Jun 2, 2012
 * Time: 10:42:56 PM
 */
public class CompanyTermsException extends Exception {
    /**
     * exception type for all database related errors
     */
    public static final String DATABASE_ERROR = "DATABASE_ERROR";

    private String exceptionType;

    public CompanyTermsException(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }
}
