package com.teamenchaire.auction;

/**
 * A {@code class} which represents a checked business exception.
 * 
 * @author Marin Taverniers
 */
public final class BusinessException extends Exception {
    private static final long serialVersionUID = 1L;

    private int code;

    /**
     * Constructs a {@code BusinessException} with the specified error code.
     * 
     * @param code The error code of the exception
     */
    public BusinessException(int code) {
        this(code, null);
    }

    /**
     * Constructs a {@code BusinessException} with specified error code and cause.
     * 
     * @param code The error code of the exception
     * @param code The cause of the exception
     */
    public BusinessException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    /**
     * Gets the error code of this exception.
     * 
     * @return the error code of the exception.
     */
    public int getCode() {
        return code;
    }
}