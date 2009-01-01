package com.poseidon.Invoice.exception;

/**
 * User: Suraj
 * Date: 7/26/12
 * Time: 10:40 PM
 */
public class InvoiceException extends Exception {
    /**
     * exception type for all database related errors
     */
    public static String DATABASE_ERROR = "DATABASE_ERROR";

    public String ExceptionType;

    public InvoiceException(String exceptionType) {
        this.ExceptionType = exceptionType;
    }

    public String getExceptionType() {
        return ExceptionType;
    }

    public void setExceptionType(String exceptionType) {
        ExceptionType = exceptionType;
    }
}
