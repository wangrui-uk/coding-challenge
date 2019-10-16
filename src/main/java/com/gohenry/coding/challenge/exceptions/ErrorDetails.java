package com.gohenry.coding.challenge.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDetails {

    String service;
    long timestamp;
    String message;
    String details;

}
