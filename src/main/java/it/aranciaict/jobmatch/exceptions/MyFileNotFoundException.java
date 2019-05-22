package it.aranciaict.jobmatch.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class MyFileNotFoundException.
 */
@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyFileNotFoundException extends RuntimeException {
    
    /**
     * Instantiates a new my file not found exception.
     *
     * @param message the message
     */
    public MyFileNotFoundException(String message) {
        super(message);
    }

    /**
     * Instantiates a new my file not found exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public MyFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}