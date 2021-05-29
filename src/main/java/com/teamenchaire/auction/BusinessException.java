package com.teamenchaire.auction;

/**
 * A {@code class} which represents a checked business exception.
 * 
 * @author Marin Taverniers
 */
public final class BusinessException extends Exception {
    private static final long serialVersionUID = 1L;

    private final BusinessErrorCode error;

    /**
     * Constructs a {@code BusinessException} with the specified error.
     * 
     * @param error The error associated to the exception
     */
    public BusinessException(BusinessErrorCode error) {
        this(error, null);
    }

    /**
     * Constructs a {@code BusinessException} with specified error and cause.
     * 
     * @param error The error associated to the exception
     * @param cause The cause of the exception
     */
    public BusinessException(BusinessErrorCode error, Throwable cause) {
        super(cause);
        this.error = error;
    }

    /**
     * Returns the code of the error associated to this exception.
     * 
     * @return the code of the error associated to the exception.
     */
    public int getCode() {
        return error.getCode();
    }

    /**
     * Returns the name of the error associated to this exception.
     * 
     * @return the name of the error associated to the exception.
     */
    public String getName() {
        return error.getName();
    }
}