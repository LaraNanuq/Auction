package com.teamenchaire.auction;

/**
 * A {@code class} which represents a checked business exception.
 * 
 * @author Marin Taverniers
 */
public final class BusinessException extends Exception {
    private static final long serialVersionUID = 1L;

    private final int code;

    public BusinessException(final int code) {
        this(code, null);
    }

    public BusinessException(final int code, final Throwable cause) {
        super(cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}