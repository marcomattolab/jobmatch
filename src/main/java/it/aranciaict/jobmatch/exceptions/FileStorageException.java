package it.aranciaict.jobmatch.exceptions;

/**
 * The Class FileStorageException.
 */
@SuppressWarnings("serial")
public class FileStorageException extends RuntimeException {
    
    /**
     * Instantiates a new file storage exception.
     *
     * @param message the message
     */
    public FileStorageException(String message) {
        super(message);
    }

    /**
     * Instantiates a new file storage exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}