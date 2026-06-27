package com.college.erp.collegemanagementsystem.exception;

/**
 * @author grish
 */

public class ValidationException extends RuntimeException{

    public ValidationException(String message) {
        super(message);
    }
}
