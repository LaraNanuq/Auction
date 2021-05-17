package com.teamenchaire.auction.dal;

/**
 * A {@code class} which defines error codes for the DAL layer.
 * 
 * @author Marin Taverniers
 */
public final class DALErrorCode {

    private DALErrorCode() {
    }

    /*
     * Error codes
     */
    public static final int DB_DATASOURCE_CONTEXT = 11001;
    public static final int DB_CONNECTION = 11002;
}