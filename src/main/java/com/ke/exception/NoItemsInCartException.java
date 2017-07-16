package com.ke.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class NoItemsInCartException extends RuntimeException {

    public NoItemsInCartException() {
        super("No items in cart!");
    }
}
