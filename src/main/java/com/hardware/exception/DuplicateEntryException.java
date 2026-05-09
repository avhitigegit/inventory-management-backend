package com.hardware.exception;

public class DuplicateEntryException extends RuntimeException {

    public DuplicateEntryException(String message) {
        super(message);
    }

    public DuplicateEntryException(String field, String value) {
        super(field + " already exists: " + value);
    }
}
