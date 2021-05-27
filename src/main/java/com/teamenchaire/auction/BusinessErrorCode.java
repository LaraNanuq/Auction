package com.teamenchaire.auction;

/**
 * An {@code interface} which defines an error associated with a business
 * exception.
 * 
 * @author Marin Taverniers
 */
public interface BusinessErrorCode {
    int getCode();

    String getName();
}