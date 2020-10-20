package com.apollo.service.exception;

public class BadOwnerException extends RuntimeException {

    public BadOwnerException() {
        super("Your're not the owner of this. So don't touch it...");
    }
}
