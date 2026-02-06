package io.github.adidych.apiversioning.exception;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Getter
@Accessors(chain = true, fluent = true)
public class InvalidVersioningException extends RuntimeException {

    private final String message;
    private final String error = "server_error";
    private final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public InvalidVersioningException(String message) {
        this.message = message;
    }
}
