package com.poseidon.customer.exception;

public class CustomerException extends Exception {
    /**
     * exception type for all database related errors.
     */
    public static final String DATABASE_ERROR = "DATABASE_ERROR";

    public static final long serialVersionUID = 4328743;
    private final String exceptionType;

    public CustomerException(final String exceptionType) {
        super();
        this.exceptionType = exceptionType;
    }

    public String getExceptionType() {
        return exceptionType;
    }
}
