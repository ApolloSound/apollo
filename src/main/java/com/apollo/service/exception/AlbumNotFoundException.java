package com.apollo.service.exception;

public class AlbumNotFoundException extends RuntimeException {

    public AlbumNotFoundException() {
        super("Album not found");
    }

}
