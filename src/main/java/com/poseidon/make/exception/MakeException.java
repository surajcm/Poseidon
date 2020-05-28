package com.poseidon.make.exception;

/**
 * user: Suraj.
 * Date: Jun 2, 2012
 * Time: 7:26:13 PM
 */
public class MakeException extends Exception {
    /**
     * exception type for all database related errors.
     */
    public static final String DATABASE_ERROR = "DATABASE_ERROR";

    public final String exceptionType;
    public static final long serialVersionUID = 4328746;

    public MakeException(final String exceptionType) {
        super();
        this.exceptionType = exceptionType;
    }

    public String getExceptionType() {
        return exceptionType;
    }
}
