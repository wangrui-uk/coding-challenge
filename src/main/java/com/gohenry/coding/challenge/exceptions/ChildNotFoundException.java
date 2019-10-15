package com.gohenry.coding.challenge.exceptions;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChildNotFoundException extends RuntimeException {

    String message = null;

    public ChildNotFoundException() {
        this.message = "Unable to find child";
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
