package org.informatics.exception;

public class EmployeeAlreadyWorkingException extends Exception {
    public EmployeeAlreadyWorkingException(String message) {
        super(message);
    }
}