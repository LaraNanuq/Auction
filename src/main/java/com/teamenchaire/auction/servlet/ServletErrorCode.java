package com.teamenchaire.auction.servlet;

/**
 * A {@code class} which defines error codes for the servlet layer.
 * 
 * @author Marin Taverniers
 */
public final class ServletErrorCode {

    private ServletErrorCode() {
    }

    /* Account */
    public static final int ACCOUNT_CREATE_PASSWORD_CHECK_INVALID = 30101;
    public static final int ACCOUNT_EDIT_OLD_PASSWORD_INVALID = 30201;
    public static final int ACCOUNT_DELETE_CONFIRMATION_INVALID = 30301;

    /* Auction */
    public static final int AUCTION_LIST_FILTER_GROUP_EMPTY = 30401;
}