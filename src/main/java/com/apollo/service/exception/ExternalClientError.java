package com.apollo.service.exception;

public class ExternalClientError extends RuntimeException {

    public ExternalClientError() {
        super("There's been a problem with the external API...");
    }
}
