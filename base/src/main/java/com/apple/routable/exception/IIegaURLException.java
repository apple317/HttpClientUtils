package com.apple.routable.exception;

/**
 * Created by apple_hsp on 17/6/19.
 */

public class IIegaURLException extends RuntimeException{
    public IIegaURLException() {
    }

    public IIegaURLException(String message) {
        super(message);
    }

    public IIegaURLException(String message, Throwable cause) {
        super(message, cause);
    }

}
