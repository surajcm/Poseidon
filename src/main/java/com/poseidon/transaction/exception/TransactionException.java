package com.poseidon.transaction.exception;

/**
 * user: Suraj.
 * Date: Jun 2, 2012
 * Time: 4:04:19 PM
 */
public class TransactionException extends Exception {
    public static final long serialVersionUID = 4328743;
    /**
     * exception type for all database related errors.
     */
    public static final String DATABASE_ERROR = "DATABASE_ERROR";

    public final String exceptionType;

    public TransactionException(final String exceptionType) {
        super();
        this.exceptionType = exceptionType;
    }

    public String getExceptionType() {
        return exceptionType;
    }

}
