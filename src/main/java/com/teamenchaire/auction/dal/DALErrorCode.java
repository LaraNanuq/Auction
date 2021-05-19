package com.teamenchaire.auction.dal;

/**
 * A {@code class} which defines error codes for the data access layer.
 * 
 * @author Marin Taverniers
 */
public final class DALErrorCode {

    private DALErrorCode() {
    }

    public static final int DB_DATASOURCE_CONTEXT = 10101;
    public static final int DB_CONNECTION = 10102;
    public static final int SQL_INSERT = 10201;
    public static final int SQL_UPDATE = 10202;
    public static final int SQL_DELETE = 10203;
    public static final int SQL_SELECT = 10204;
}