package com.poseidon.reports.exception;

/**
 * user: Suraj
 * Date: Jun 3, 2012
 * Time: 10:39:27 AM
 */
public class ReportsException extends Exception {
    /**
     * exception type for all database related errors
     */
    public static final String DATABASE_ERROR = "DATABASE_ERROR";

    private String exceptionType;

    public ReportsException(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }
}
