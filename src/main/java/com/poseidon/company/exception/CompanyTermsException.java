package com.poseidon.company.exception;


public class CompanyTermsException extends Exception {
    /**
     * exception type for all database related errors.
     */
    public static final String DATABASE_ERROR = "DATABASE_ERROR";

    private String exceptionType;
    public static final long serialVersionUID = 4328745;

    public CompanyTermsException(final String exceptionType) {
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
