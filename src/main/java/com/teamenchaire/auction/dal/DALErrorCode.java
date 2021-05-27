package com.teamenchaire.auction.dal;

import com.teamenchaire.auction.BusinessErrorCode;

/**
 * An {@code enum} list of error codes for the data access layer.
 * 
 * @author Marin Taverniers
 */
public enum DALErrorCode implements BusinessErrorCode {

    /* Database */
    DB_DATASOURCE_CONTEXT(10101),
    DB_CONNECTION(10102),

    /* Query */
    SQL_INSERT(10201),
    SQL_UPDATE(10202),
    SQL_DELETE(10203),
    SQL_SELECT(10204);

    private int code;
    private String name;

    private DALErrorCode(int code) {
        this.code = code;
        this.name = this.name();
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }
}