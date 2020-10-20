package com.apollo.service.exception;

public class TrackNotFoundException extends RuntimeException {

    public TrackNotFoundException() {
        super("Track not found");
    }

}
