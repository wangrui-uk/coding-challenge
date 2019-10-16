package com.gohenry.coding.challenge.exceptions;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParentNotFoundException extends RuntimeException {

    String message = null;

    public ParentNotFoundException() {
        this.message = "Unable to find parent";
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
