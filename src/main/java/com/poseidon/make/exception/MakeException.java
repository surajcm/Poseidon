package com.poseidon.make.exception;

/**
 * user: Suraj
 * Date: Jun 2, 2012
 * Time: 7:26:13 PM
 */
public class MakeException extends Exception {
    /**
     * exception type for all database related errors
     */
    public static final String DATABASE_ERROR = "DATABASE_ERROR";

    public String exceptionType;

    public MakeException(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }
}
