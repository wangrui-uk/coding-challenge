package com.gohenry.coding.challenge.exceptions;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class DatabaseConnectionException extends RuntimeException {

    String message = null;

    public DatabaseConnectionException(Throwable throwable) {
        this.message = throwable.getMessage();
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
