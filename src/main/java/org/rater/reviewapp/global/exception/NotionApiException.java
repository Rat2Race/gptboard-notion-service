package org.rater.reviewapp.global.exception;

public class NotionApiException extends RuntimeException {

    public NotionApiException(String message) {
        super(message);
    }

    public NotionApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
