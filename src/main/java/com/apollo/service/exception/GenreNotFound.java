package com.apollo.service.exception;

public class GenreNotFound extends RuntimeException {

    public GenreNotFound() {
        super("Genre not found");
    }
}
