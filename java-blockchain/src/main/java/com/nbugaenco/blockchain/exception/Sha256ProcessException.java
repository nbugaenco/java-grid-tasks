package com.nbugaenco.blockchain.exception;

/**
 * This exception is thrown when there is an error during the SHA-256 hashing process.
 *
 * @author nbugaenco
 */
public class Sha256ProcessException extends RuntimeException {

    /**
     * Constructs a Sha256ProcessException with the specified detail message.
     *
     * @param message the detail message explaining the cause of the exception
     */
    public Sha256ProcessException(String message) {
        super(message);
    }
}
