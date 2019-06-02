package com.poseidon.invoice.exception;

/**
 * user: Suraj
 * Date: 7/26/12
 * Time: 10:40 PM
 */
public class InvoiceException extends Exception {
    /**
     * exception type for all database related errors
     */
    public static final String DATABASE_ERROR = "DATABASE_ERROR";

    private String exceptionType;
    public static final long serialVersionUID = 4328744;

    public InvoiceException(String exceptionType) {
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
