package com.nbugaenco.blockchain.exception;

/**
 * This exception is thrown when an invalid block is encountered in the blockchain.
 *
 * @author nbugaenco
 */
public class InvalidBlockException extends RuntimeException {

    /**
     * Constructs an InvalidBlockException with the specified detail message.
     *
     * @param message the detail message explaining the cause of the exception
     */
    public InvalidBlockException(String message) {
        super(message);
    }
}
