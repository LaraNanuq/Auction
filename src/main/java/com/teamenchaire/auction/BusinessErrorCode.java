package com.teamenchaire.auction;

/**
 * An {@code interface} which defines an error associated with a business
 * exception.
 * 
 * @author Marin Taverniers
 */
public interface BusinessErrorCode {

    /**
     * Returns the code of this error.
     * 
     * @return the code of the error.
     */
    int getCode();

    /**
     * Returns the name of this error.
     * 
     * @return the name of the error.
     */
    String getName();
}