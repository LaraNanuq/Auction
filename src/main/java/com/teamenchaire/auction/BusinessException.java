package com.teamenchaire.auction;

/**
 * A {@code class} which represents a checked business exception.
 * 
 * @author Marin Taverniers
 */
public final class BusinessException extends Exception {
    private static final long serialVersionUID = 1L;

    private BusinessErrorCode error;

    /**
     * Constructs a {@code BusinessException} with the specified error.
     * 
     * @param error The error code of the exception
     */
    public BusinessException(BusinessErrorCode error) {
        this(error, null);
    }

    /**
     * Constructs a {@code BusinessException} with specified error and cause.
     * 
     * @param error The error code of the exception
     * @param cause The cause of the exception
     */
    public BusinessException(BusinessErrorCode error, Throwable cause) {
        super(cause);
        this.error = error;
    }

    /**
     * Gets the error code of this exception.
     * 
     * @return the error code of the exception.
     */
    public int getCode() {
        return error.getCode();
    }

    /**
     * Gets the error name of this exception.
     * 
     * @return the error name of the exception.
     */
    public String getName() {
        return error.getName();
    }
}