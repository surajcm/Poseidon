package com.poseidon.Make.exception;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 7:26:13 PM
 */
public class MakeException extends Exception {
    /**
     * exception type for all database related errors
     */
    public static String DATABASE_ERROR = "DATABASE_ERROR";

    public String ExceptionType;

    public MakeException(String exceptionType) {
        this.ExceptionType = exceptionType;
    }

    public String getExceptionType() {
        return ExceptionType;
    }

    public void setExceptionType(String exceptionType) {
        ExceptionType = exceptionType;
    }
}
