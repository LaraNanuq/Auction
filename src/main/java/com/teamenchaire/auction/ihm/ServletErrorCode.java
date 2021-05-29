package com.teamenchaire.auction.ihm;

import com.teamenchaire.auction.BusinessErrorCode;

/**
 * An {@code enum} list of error codes for the servlet layer.
 * 
 * @author Marin Taverniers
 */
public enum ServletErrorCode implements BusinessErrorCode {

    /* Account */
    ACCOUNT_SET_PASSWORD_CHECK_INVALID(30101),
    ACCOUNT_EDIT_OLD_PASSWORD_INVALID(30102),
    ACCOUNT_DELETE_CONFIRMATION_NULL(30103),

    /* Auction */
    AUCTION_LIST_GROUP_NULL(30201);

    private int code;
    private String name;

    private ServletErrorCode(int code) {
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