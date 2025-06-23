package org.rater.reviewapp.global.exception;

public class NotionException extends RuntimeException {

    public NotionException(String message) {
        super(message);
    }

    public NotionException(String message, Throwable cause) {
        super(message, cause);
    }
}
