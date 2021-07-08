package com.poseidon.invoice.exception;

public class InvoiceException extends Exception {
    /**
     * exception type for all database related errors.
     */
    public static final String DATABASE_ERROR = "DATABASE_ERROR";

    private final Exception exceptionType;
    public static final long serialVersionUID = 4328744;

    public InvoiceException(final Exception exceptionType) {
        super();
        this.exceptionType = exceptionType;
    }

    public Exception getExceptionType() {
        return exceptionType;
    }
}
