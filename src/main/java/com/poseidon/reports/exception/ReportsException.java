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
    public static String DATABASE_ERROR = "DATABASE_ERROR";

    public String ExceptionType;

    public ReportsException(String exceptionType) {
        this.ExceptionType = exceptionType;
    }

    public String getExceptionType() {
        return ExceptionType;
    }

    public void setExceptionType(String exceptionType) {
        ExceptionType = exceptionType;
    }
}
