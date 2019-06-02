package com.poseidon.transaction.exception;

/**
 * user: Suraj
 * Date: Jun 2, 2012
 * Time: 4:04:19 PM
 */
public class TransactionException extends Exception {
    /**
     * exception type for all database related errors
     */
    public static final String DATABASE_ERROR = "DATABASE_ERROR";

    public String exceptionType;

    public TransactionException(String exceptionType) {
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
