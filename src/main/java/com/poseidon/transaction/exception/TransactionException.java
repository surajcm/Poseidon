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
    public static String DATABASE_ERROR = "DATABASE_ERROR";

    public String ExceptionType;

    public TransactionException(String exceptionType) {
        this.ExceptionType = exceptionType;
    }

    public String getExceptionType() {
        return ExceptionType;
    }

    public void setExceptionType(String exceptionType) {
        ExceptionType = exceptionType;
    }
}
