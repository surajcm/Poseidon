package com.poseidon.reports.exception;

/**
 * user: Suraj.
 * Date: Jun 3, 2012
 * Time: 10:39:27 AM
 */
public class ReportsException extends Exception {
    /**
     * exception type for all database related errors.
     */
    public static final String DATABASE_ERROR = "DATABASE_ERROR";
    public static final long serialVersionUID = 4328748;

    private final String exceptionType;

    public ReportsException(final String exceptionType) {
        super();
        this.exceptionType = exceptionType;
    }

    public String getExceptionType() {
        return exceptionType;
    }
}
