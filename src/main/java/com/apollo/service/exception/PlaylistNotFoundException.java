package com.apollo.service.exception;


public class PlaylistNotFoundException extends RuntimeException {

    public PlaylistNotFoundException() {
        super("No playlist was found");
    }

}
