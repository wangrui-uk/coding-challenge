package com.gohenry.coding.challenge.exceptions;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParentExistedException extends RuntimeException {

    String message = null;

    public ParentExistedException(String email) {
        this.message = String.format("Parent email %s already registered, please use a different email address", email);
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
